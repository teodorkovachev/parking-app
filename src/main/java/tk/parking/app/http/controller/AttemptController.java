package tk.parking.app.http.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import tk.parking.app.exception.ConcurrentParkingEntryException;
import tk.parking.app.http.request.EntryAttemptRequest;
import tk.parking.app.http.request.ExitAttemptRequest;
import tk.parking.app.http.response.AttemptResponse;
import tk.parking.app.service.AttemptsService;
import tk.parking.app.service.RetryAttemptService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/attempt")
public class AttemptController {

    @Autowired
    private AttemptsService attemptsService;

    @Autowired
    private RetryAttemptService retryAttemptService;

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(
            value = "/entry/{entryId}",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public AttemptResponse entryAttempt(@PathVariable final Integer entryId,
                                        @Valid @RequestBody final EntryAttemptRequest entryAttemptRequest) {
        log.info("Vehicle with ID: {} of type {} is trying to enter a parking through entry: {}",
                entryAttemptRequest.getVehicleId(),
                entryAttemptRequest.getVehicleType(),
                entryId);
        return attemptsService.attemptEntry(entryId,
                entryAttemptRequest.getVehicleType(),
                entryAttemptRequest.getVehicleId());
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(
            value = "/exit/{exitId}",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public AttemptResponse exitAttempt(@PathVariable final Integer exitId,
                                       @Valid @RequestBody final ExitAttemptRequest exitAttemptRequest) {
        log.info("Vehicle with ID: {} is trying to exit a parking through exit: {}",
                exitAttemptRequest.getVehicleId(),
                exitId);
        return attemptsService.attemptExit(exitId, exitAttemptRequest);
    }

    @ExceptionHandler(ConcurrentParkingEntryException.class)
    public AttemptResponse retryAfterOptimisticLockFailure(final HttpServletRequest request, final ConcurrentParkingEntryException e) {
        return retryAttemptService.retryRequest(request.getRequestURL().toString(), e.vehicleId, e.vehicleType);
    }
}
