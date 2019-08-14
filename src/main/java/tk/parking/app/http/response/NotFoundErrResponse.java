package tk.parking.app.http.response;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import tk.parking.app.common.ParkingSegment;

@Builder
@Getter
@ToString
public class NotFoundErrResponse {
    private final ParkingSegment notFound;
}
