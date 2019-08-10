package tk.parking.app.http.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@ToString
@Builder(builderClassName = "LevelBuilder")
@JsonDeserialize(builder = LevelDTO.LevelBuilder.class)
public class LevelDTO {

    @NotNull(message = "Missing level number")
    private final Long level;

    @Valid
    @Size(min = 2)
    private final List<EntryDTO> parkingEntries;

    @Valid
    @Size(min = 2)
    private final List<ExitDTO> parkingExits;

    @Valid
    @Size(min = 1)
    private final List<SpotDTO> parkingSpots;

    @JsonPOJOBuilder(withPrefix = "")
    public static class LevelBuilder {
    }
}
