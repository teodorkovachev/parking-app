package tk.parking.app.http.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Getter
@ToString
@Builder(builderClassName = "ExitAttemptRequestBuilder")
@JsonDeserialize(builder = ExitAttemptRequest.ExitAttemptRequestBuilder.class)
public class ExitAttemptRequest {

    @NotBlank
    private String vehicleId;

    @JsonPOJOBuilder(withPrefix = "")
    public static class ExitAttemptRequestBuilder {
    }
}
