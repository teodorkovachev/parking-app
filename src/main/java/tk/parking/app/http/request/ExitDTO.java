package tk.parking.app.http.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Getter
@ToString
@Builder(builderClassName = "ExitBuilder")
@JsonDeserialize(builder = ExitDTO.ExitBuilder.class)
public class ExitDTO {

    @NotBlank(message = "Missing exit address")
    private final String exitAddress;

    @JsonPOJOBuilder(withPrefix = "")
    public static class ExitBuilder {
    }
}
