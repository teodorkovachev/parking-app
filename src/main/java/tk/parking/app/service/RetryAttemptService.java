package tk.parking.app.service;

import tk.parking.app.common.VehicleType;
import tk.parking.app.http.response.AttemptResponse;

public interface RetryAttemptService {

    AttemptResponse retryRequest(final String url, final String vehicleId, final VehicleType vehicleType);
}
