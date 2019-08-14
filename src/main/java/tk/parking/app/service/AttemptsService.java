package tk.parking.app.service;

import tk.parking.app.common.VehicleType;
import tk.parking.app.http.request.ExitAttemptRequest;
import tk.parking.app.http.response.AttemptResponse;

public interface AttemptsService {
    AttemptResponse attemptEntry(final int entryId, final VehicleType vehicleType, final String vehicleId);

    AttemptResponse attemptExit(final int exitId, final ExitAttemptRequest exitAttemptRequest);

    AttemptResponse handleVehicleDuplication(final VehicleType vehicleType, final String vehicleId, final int entryId);
}
