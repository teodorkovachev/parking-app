package tk.parking.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.parking.app.entity.ParkingSpot;
import tk.parking.app.repo.ParkingSpotRepository;

import java.util.List;

@Service
public class ParkingSpotServiceImpl implements ParkingSpotService {

    @Autowired
    private ParkingSpotRepository parkingSpotRepository;

    @Override
    public List<ParkingSpot> getAllParkingSpots(final Integer levelFilter) {
        if (levelFilter == null) {
            return parkingSpotRepository.findAll();
        }
        return parkingSpotRepository.findByParkingLevel_Level(levelFilter);
    }
}
