package tk.parking.app.exception;

import tk.parking.app.common.VehicleType;

public class ConcurrentParkingEntryException extends RuntimeException {

    public final int entryId;

    public final String vehicleId;

    public final VehicleType vehicleType;

    public ConcurrentParkingEntryException(final VehicleType vehicleType, final String vehicleId, final int entryId) {
        this.vehicleType = vehicleType;
        this.vehicleId = vehicleId;
        this.entryId = entryId;
    }
}
