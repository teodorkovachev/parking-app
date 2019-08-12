package tk.parking.app.service;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.StaleObjectStateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.parking.app.common.AttemptStatus;
import tk.parking.app.common.VehicleType;
import tk.parking.app.entity.ParkingExit;
import tk.parking.app.entity.ParkingSpot;
import tk.parking.app.exception.ConcurrentParkingEntryException;
import tk.parking.app.http.request.EntryAttemptRequest;
import tk.parking.app.http.request.ExitAttemptRequest;
import tk.parking.app.repo.ParkingSpotRepository;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
public class AttemptsServiceImpl implements AttemptsService {

    @Autowired
    private ParkingSpotRepository parkingSpotRepository;

    @Override
    public AttemptStatus attemptEntry(final int entryId, final VehicleType vehicleType, final String vehicleId) {
        log.trace("Querying the database for free parking spots of type {} on level with entry id {}", vehicleType, entryId);
        List<ParkingSpot> freeParkingSpots = parkingSpotRepository.findFreeParkingSpotsByTypeAndEntry(vehicleType, entryId, PageRequest.of(0, 1));
        log.trace("Found {} free parking spots of type {} on level with entry {}", freeParkingSpots.size(), vehicleType, entryId);
        if (!freeParkingSpots.isEmpty()) {
            final ParkingSpot ps = freeParkingSpots.iterator().next();
            ps.setVehicleId(vehicleId);
            try {
                parkingSpotRepository.flush();
                //TODO Here write the entry attempt log success record
                return AttemptStatus.SUCCESS;
            } catch (ObjectOptimisticLockingFailureException e) {
                log.trace("Because of concurrent processing the selected parking spot was taken. Trying to find another one");
                throw new ConcurrentParkingEntryException(vehicleType, vehicleId, entryId);
            }
        }
        log.debug("There were not free parking spaces of type {} on level with entry {}. Rejecting parking request of vehicle {}", vehicleType, entryId, vehicleId);
        //TODO here create the records for faile entry attempts
        return AttemptStatus.FAILED;
    }

    @Override
    public AttemptStatus attemptExit(final int exitId, final ExitAttemptRequest exitAttemptRequest) {
        final String vehicleId = exitAttemptRequest.getVehicleId();
        final ParkingSpot parkingSpot = parkingSpotRepository.findByVehicleId(vehicleId);
        final Set<Integer> availableExitIds = parkingSpot.getParkingLevel().getParkingExits()
                .stream().map(ParkingExit::getExitId).collect(Collectors.toSet());
        if (availableExitIds.contains(exitId)) {
            parkingSpot.setVehicleId(null);
            return AttemptStatus.SUCCESS;
        }
        log.debug("Exit with id {} does not co-exist on the same level as the parking spot where vehicle with id {} is placed",
                exitId, vehicleId);
        //TODO here create the records for faile entry attempts
        return AttemptStatus.FAILED;
    }
}
