package tk.parking.app.exception;

import tk.parking.app.common.ParkingSegment;

public class SegmentNotFoundException extends RuntimeException {

    public final ParkingSegment segment;

    public SegmentNotFoundException(final String message, final ParkingSegment segment) {
        super(message);
        this.segment = segment;
    }
}
