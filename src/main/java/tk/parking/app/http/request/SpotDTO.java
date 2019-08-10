package tk.parking.app.http.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import tk.parking.app.common.VehicleType;

import javax.validation.constraints.NotNull;

@Getter
@ToString
@Builder(builderClassName = "SpotBuilder")
@JsonDeserialize(builder = SpotDTO.SpotBuilder.class)
public class SpotDTO {

    @NotNull(message = "Missing vehicle type")
    private final VehicleType vehicleType;

    @JsonPOJOBuilder(withPrefix = "")
    public static class SpotBuilder {
    }
}
