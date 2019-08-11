package tk.parking.app.http.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

@ToString
@Builder
@Getter
public class CreateParkingResponse {
    @NonNull
    private Integer parkingId;
}
