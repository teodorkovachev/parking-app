package tk.parking.app.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.parking.app.entity.*;
import tk.parking.app.http.request.*;
import tk.parking.app.repo.ParkingRepo;

import java.util.stream.Collectors;

@Slf4j
@Service
public class ParkingServiceImpl implements ParkingService {

    @Autowired
    private ParkingRepo parkingRepo;

    @Override
    public Integer createParking(final ParkingDTO parkingDTO) {
        return parkingRepo.save(transformDTOtoEntity(parkingDTO)).getParkingId();
    }

    private static Parking transformDTOtoEntity(final ParkingDTO parkingDTO) {
        return Parking.builder()
                .parkingName(parkingDTO.getParkingName())
                .parkingLevels(
                        parkingDTO.getParkingLevels()
                                .stream()
                                .<ParkingLevel>map(ParkingServiceImpl::transformDTOtoEntity)
                                .collect(Collectors.toList()))
                .build();
    }

    private static ParkingLevel transformDTOtoEntity(final LevelDTO levelDTO) {
        return ParkingLevel.builder()
                .level(levelDTO.getLevel())
                .entries(levelDTO.getParkingEntries()
                        .stream()
                        .<ParkingEntry>map(ParkingServiceImpl::transformDTOtoEntity)
                        .collect(Collectors.toList()))
                .parkingExits(levelDTO.getParkingExits()
                        .stream()
                        .<ParkingExit>map(ParkingServiceImpl::transformDTOtoEntity)
                        .collect(Collectors.toList()))
                .parkingSpots(levelDTO.getParkingSpots()
                        .stream()
                        .<ParkingSpot>map(ParkingServiceImpl::transformDTOtoEntity)
                        .collect(Collectors.toList()))
                .build();
    }

    private static ParkingEntry transformDTOtoEntity(final EntryDTO entryDTO) {
        return ParkingEntry.builder().address(entryDTO.getEntryAddress()).build();
    }

    private static ParkingExit transformDTOtoEntity(final ExitDTO exitDTO) {
        return ParkingExit.builder().address(exitDTO.getExitAddress()).build();
    }

    private static ParkingSpot transformDTOtoEntity(final SpotDTO spotDTO) {
        return ParkingSpot.builder().vehicleType(spotDTO.getVehicleType()).build();
    }
}
