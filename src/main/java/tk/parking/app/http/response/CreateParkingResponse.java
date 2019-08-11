package tk.parking.app.http.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import tk.parking.app.entity.Parking;

@ToString
@Builder
@Getter
public class CreateParkingResponse {
    @NonNull
    private Parking parking;
}
