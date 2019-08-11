package tk.parking.app.service;

import tk.parking.app.entity.Parking;
import tk.parking.app.http.request.ParkingDTO;

public interface ParkingService {
    Parking createParking(final ParkingDTO parkingDTO);
}
