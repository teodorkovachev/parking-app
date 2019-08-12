package tk.parking.app.service;

import tk.parking.app.common.AttemptStatus;
import tk.parking.app.common.VehicleType;
import tk.parking.app.http.request.EntryAttemptRequest;
import tk.parking.app.http.request.ExitAttemptRequest;

public interface AttemptsService {
    AttemptStatus attemptEntry(final int entryId, final VehicleType vehicleType, final String vehicleId);

    AttemptStatus attemptExit(final int exitId, final ExitAttemptRequest exitAttemptRequest);
}
