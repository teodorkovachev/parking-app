package tk.parking.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.parking.app.common.ParkingSegment;
import tk.parking.app.entity.Parking;
import tk.parking.app.entity.ParkingLevel;
import tk.parking.app.entity.ParkingSpot;
import tk.parking.app.exception.SegmentNotFoundException;
import tk.parking.app.repo.ParkingRepo;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ParkingSpotServiceImpl implements ParkingSpotService {

    @Autowired
    private ParkingRepo parkingRepo;

    @Override
    public List<ParkingSpot> getAllParkingSpots(final int parkingId, final Integer levelFilter) {
        Optional<Parking> parkingOpt = parkingRepo.findById(parkingId);
        if (parkingOpt.isPresent()) {
            if (levelFilter != null) {
                Optional<ParkingLevel> levelOpt = parkingOpt.get().getParkingLevels()
                        .stream()
                        .filter(l -> l.getLevel().equals(levelFilter))
                        .findFirst();
                if (levelOpt.isPresent()) {
                    return levelOpt.get().getParkingSpots();
                }
                throw new SegmentNotFoundException("Level " + levelFilter + " not found in parking: " + parkingId, ParkingSegment.LEVEL);
            }
            return parkingOpt.get().getParkingLevels()
                    .stream()
                    .<ParkingSpot>flatMap(l -> l.getParkingSpots().stream())
                    .collect(Collectors.toList());
        }
        throw new SegmentNotFoundException("Parking with id: " + parkingId + " not Found", ParkingSegment.PARKING);
    }
}
