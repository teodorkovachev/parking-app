package tk.parking.app.http.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import tk.parking.app.validation.LevelSequenceConstraint;

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
    @LevelSequenceConstraint
    private final List<LevelDTO> parkingLevels;

    @JsonPOJOBuilder(withPrefix = "")
    public static class ParkingBuilder {

    }
}
