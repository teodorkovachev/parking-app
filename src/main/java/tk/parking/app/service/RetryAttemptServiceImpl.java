package tk.parking.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import tk.parking.app.common.VehicleType;
import tk.parking.app.http.request.EntryAttemptRequest;
import tk.parking.app.http.response.AttemptResponse;

@Service
public class RetryAttemptServiceImpl implements RetryAttemptService {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public AttemptResponse retryRequest(final String url, final String vehicleId, final VehicleType vehicleType) {
        return restTemplate.postForObject(url, EntryAttemptRequest.builder()
                        .vehicleId(vehicleId)
                        .vehicleType(vehicleType)
                        .build(),
                AttemptResponse.class);
    }
}
