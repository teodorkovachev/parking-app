package tk.parking.app.common;

import com.fasterxml.jackson.annotation.JsonValue;

public enum FailReason {

    NOT_ENOUGH_SPACE("No parking spaces for this type of vehicle on this level"),
    VEHICLE_NOT_FOUND("Vehicle with the selected id was not found"),
    EXIT_UNREACHABLE("The selected exit is unreachable from the level where the vehicle is located"),
    DUPLICATE_VEHICLE("Vehicle with this id already exists in the parking");

    FailReason(final String message) {
        this.message = message;
    }

    @JsonValue
    private String message;
}
