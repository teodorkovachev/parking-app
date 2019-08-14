package tk.parking.app.service;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.parking.app.common.AttemptStatus;
import tk.parking.app.common.FailReason;
import tk.parking.app.common.ParkingSegment;
import tk.parking.app.common.VehicleType;
import tk.parking.app.entity.EntryAttempt;
import tk.parking.app.entity.ExitAttempt;
import tk.parking.app.entity.ParkingExit;
import tk.parking.app.entity.ParkingSpot;
import tk.parking.app.exception.ConcurrentParkingEntryException;
import tk.parking.app.exception.DuplicateVehicleException;
import tk.parking.app.exception.SegmentNotFoundException;
import tk.parking.app.http.request.ExitAttemptRequest;
import tk.parking.app.http.response.AttemptResponse;
import tk.parking.app.repo.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
public class AttemptsServiceImpl implements AttemptsService {

    private static final int MSSQL_UNIQUE_INDEX_VIOLATION = 2601;
    private static final int MSSQL_UNIQUE_CONSTR_VIOLATION = 2627;

    @Autowired
    private ParkingSpotRepository parkingSpotRepository;

    @Autowired
    private EntryAttemptRepo entryAttemptRepo;

    @Autowired
    private ExitAttemptRepo exitAttemptRepo;

    @Autowired
    private EntryRepo entryRepo;

    @Autowired
    private ExitRepo exitRepo;

    @Override
    public AttemptResponse attemptEntry(final int entryId, final VehicleType vehicleType, final String vehicleId) {
        //First check whether the selected entry exists
        checkSegmentExistence(entryRepo, ParkingSegment.ENTRY, entryId);

        log.trace("Querying the database for free parking spots of type {} on level with entry id {}", vehicleType, entryId);
        List<ParkingSpot> freeParkingSpots = parkingSpotRepository.findFreeParkingSpotsByTypeAndEntry(vehicleType, entryId, PageRequest.of(0, 1));
        log.debug("Found {} free parking spots of type {} on level with entry {}", freeParkingSpots.size(), vehicleType, entryId);

        //Create the entity builder and set some data that is going to be used almost certainly
        final EntryAttempt.EntryAttemptBuilder entityBuilder = EntryAttempt.builder()
                .entryId(entryId)
                .vehicleId(vehicleId)
                .vehicleType(vehicleType);
        final AttemptResponse.AttemptResponseBuilder responseBuilder = AttemptResponse.builder();

        if (!freeParkingSpots.isEmpty()) {
            final ParkingSpot ps = freeParkingSpots.iterator().next();
            ps.setVehicleId(vehicleId);
            try {
                //Perform flush, which in case of stale object will fire an exception or otherwise will block other
                //threads trying to update the same record until this transaction is committed
                parkingSpotRepository.flush();
                //Create an attempt record
                entryAttemptRepo.save(entityBuilder.status(AttemptStatus.SUCCESS).build());
                //Create and return SUCCESS response
                return responseBuilder.attemptStatus(AttemptStatus.SUCCESS).build();
            } catch (ObjectOptimisticLockingFailureException e) {
                log.trace("Because of concurrent processing the selected parking spot was taken. Trying to find another one");
                //Throwing a custom exception in order to retry the attempt with the same vehicle and entry
                throw new ConcurrentParkingEntryException(vehicleType, vehicleId, entryId);
            } catch (DataIntegrityViolationException dive) {
                if (dive.getCause() instanceof ConstraintViolationException
                        && dive.getCause().getCause() instanceof SQLServerException) {
                    SQLServerException sqlServerException = (SQLServerException) dive.getCause().getCause();
                    if (sqlServerException.getErrorCode() == MSSQL_UNIQUE_CONSTR_VIOLATION
                            || sqlServerException.getErrorCode() == MSSQL_UNIQUE_INDEX_VIOLATION) {
                        log.info("Vehicle with id {} is already parked. Request failure", vehicleId);
                        throw new DuplicateVehicleException(vehicleType, vehicleId, entryId);
                    }
                }
                throw dive;
            }
        }

        log.debug("There were not free parking spaces of type {} on level with entry {}. " +
                "Rejecting parking request of vehicle {}", vehicleType, entryId, vehicleId);
        final String reason = "Not enough parking spaces";
        entryAttemptRepo.save(entityBuilder.status(AttemptStatus.FAILED).reason(FailReason.NOT_ENOUGH_SPACE).build());
        return responseBuilder.attemptStatus(AttemptStatus.FAILED).reason(FailReason.NOT_ENOUGH_SPACE).build();
    }

    @Override
    public AttemptResponse attemptExit(final int exitId, final ExitAttemptRequest exitAttemptRequest) {
        //First check whether the selected exit exists
        checkSegmentExistence(exitRepo, ParkingSegment.EXIT, exitId);

        final String vehicleId = exitAttemptRequest.getVehicleId();
        //Fint the parking spot where the vehicle is located
        final Optional<ParkingSpot> parkingSpotOpt = parkingSpotRepository.findByVehicleId(vehicleId);

        //Create the entity builder and set some data that is going to be used almost certainly
        final ExitAttempt.ExitAttemptBuilder entityBuilder = ExitAttempt.builder()
                .exitId(exitId)
                .vehicleId(vehicleId);
        //If the vehicle has been found within the parking
        if (parkingSpotOpt.isPresent()) {
            final ParkingSpot parkingSpot = parkingSpotOpt.get();
            //Knowing the level, where the vehicle is located we can decide whether the exit is reachable
            final Set<Integer> availableExitIds = parkingSpot.getParkingLevel().getParkingExits()
                    .stream()
                    .map(ParkingExit::getExitId)
                    .collect(Collectors.toSet());
            if (availableExitIds.contains(exitId)) {
                parkingSpot.setVehicleId(null);
                try {
                    parkingSpotRepository.flush();
                } catch (ObjectOptimisticLockingFailureException e) {
                    //We are not interested at handling that, because the worst that might have happened is the spot
                    // has been emptied by another thread
                    log.debug("Vehicle with id {} might have left through another exit", vehicleId);
                }
                exitAttemptRepo.save(entityBuilder.status(AttemptStatus.SUCCESS).build());
                return AttemptResponse.builder().attemptStatus(AttemptStatus.SUCCESS).build();
            }
            log.debug("Exit with id {} does not co-exist on the same level as the parking spot where vehicle with id {} is placed",
                    exitId, vehicleId);
            final String reason = "Entry is not on the same level as the vehicle";
            exitAttemptRepo.save(entityBuilder.status(AttemptStatus.FAILED).reason(FailReason.EXIT_UNREACHABLE).build());
            return AttemptResponse.builder()
                    .attemptStatus(AttemptStatus.FAILED)
                    .reason(FailReason.EXIT_UNREACHABLE).build();
        }
        final String reason = "No vehicle with such id found";
        exitAttemptRepo.save(entityBuilder.status(AttemptStatus.FAILED).reason(FailReason.VEHICLE_NOT_FOUND).build());
        return AttemptResponse.builder().
                attemptStatus(AttemptStatus.FAILED).
                reason(FailReason.VEHICLE_NOT_FOUND).
                build();
    }

    @Override
    public AttemptResponse handleVehicleDuplication(VehicleType vehicleType, String vehicleId, int entryId) {
        entryAttemptRepo.save(EntryAttempt.builder()
                .entryId(entryId)
                .vehicleId(vehicleId)
                .vehicleType(vehicleType)
                .reason(FailReason.DUPLICATE_VEHICLE)
                .status(AttemptStatus.FAILED)
                .build());
        return AttemptResponse.builder()
                .attemptStatus(AttemptStatus.FAILED)
                .reason(FailReason.DUPLICATE_VEHICLE)
                .build();
    }

    private void checkSegmentExistence(CrudRepository<?, Integer> repo, ParkingSegment segment, Integer segmentId) {
        if (!repo.existsById(segmentId)) {
            final String message = String.format("Segment of Type: %s and with id: %d was not found", segment, segmentId);
            log.info(message);
            throw new SegmentNotFoundException(message, segment);
        }
    }
}
