package tk.parking.app.exception;

import tk.parking.app.common.VehicleType;

public class ConcurrentParkingEntryException extends ParkingAttemptException {

    public ConcurrentParkingEntryException(VehicleType vehicleType, String vehicleId, int entryId) {
        super("Concurrent parking attempt occurred", vehicleType, vehicleId, entryId);
    }
}
