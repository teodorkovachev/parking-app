package tk.parking.app.http.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import tk.parking.app.common.VehicleType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@ToString
@Builder(builderClassName = "EntryAttemptRequestBuilder")
@JsonDeserialize(builder = EntryAttemptRequest.EntryAttemptRequestBuilder.class)
public class EntryAttemptRequest {

    @NotNull
    private VehicleType vehicleType;

    @NotBlank
    private String vehicleId;

    @JsonPOJOBuilder(withPrefix = "")
    public static class EntryAttemptRequestBuilder {
    }
}
