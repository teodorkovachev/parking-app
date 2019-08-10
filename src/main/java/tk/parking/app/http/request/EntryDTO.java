package tk.parking.app.http.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Getter
@ToString
@Builder(builderClassName = "EntryBuilder")
@JsonDeserialize(builder = EntryDTO.EntryBuilder.class)
public class EntryDTO {

    @NotBlank(message = "Missing entry address")
    private final String entryAddress;

    @JsonPOJOBuilder(withPrefix = "")
    public static class EntryBuilder {
    }
}
