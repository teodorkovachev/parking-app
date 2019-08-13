package tk.parking.app.service;

import tk.parking.app.entity.ParkingSpot;

import java.util.List;

public interface ParkingSpotService {
    List<ParkingSpot> getAllParkingSpots(final Integer levelFilter);
}
