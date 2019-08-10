package tk.parking.app.service;

import tk.parking.app.http.request.ParkingDTO;

public interface ParkingService {
    Integer createParking(final ParkingDTO parkingDTO);
}
