package tk.parking.app.http.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@ToString
@Builder(builderClassName = "ParkingBuilder")
@JsonDeserialize(builder = ParkingDTO.ParkingBuilder.class)
public class ParkingDTO {

    @NotBlank (message = "Please enter a parking name")
    private final String parkingName;

    @Valid
    @Size(min = 1)
    private final List<LevelDTO> parkingLevels;

    @JsonPOJOBuilder(withPrefix = "")
    public static class ParkingBuilder {

    }
}
