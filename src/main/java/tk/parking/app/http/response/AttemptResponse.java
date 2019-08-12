package tk.parking.app.http.response;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import tk.parking.app.common.AttemptStatus;

@Builder(builderClassName = "AttemptResponseBuilder")
@Getter
@ToString
@JsonDeserialize(builder = AttemptResponse.AttemptResponseBuilder.class)
public class AttemptResponse {
    private final AttemptStatus attemptStatus;

    @JsonPOJOBuilder(withPrefix = "")
    public static class AttemptResponseBuilder {
    }
}
