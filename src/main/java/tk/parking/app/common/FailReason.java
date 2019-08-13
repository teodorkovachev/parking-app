package tk.parking.app.common;

public enum FailReason {

    NOT_ENOUGH_SPACE("No parking spaces for this type of vehicle on this level"),
    VEHICLE_NOT_FOUND("Vehicle with the selected id was not found"),
    EXIT_UNREACHABLE("The selected exit is unreachable from the level where the vehicle is located");

    FailReason(final String message) {
        this.message = message;
    }

    private String message;
}
