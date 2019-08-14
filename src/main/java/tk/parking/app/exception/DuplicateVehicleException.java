package tk.parking.app.exception;

import tk.parking.app.common.VehicleType;

public class DuplicateVehicleException extends ParkingAttemptException {


    public DuplicateVehicleException(VehicleType vehicleType, String vehicleId, int entryId) {
        super("Vehicle with the same id already exists", vehicleType, vehicleId, entryId);
    }
}
