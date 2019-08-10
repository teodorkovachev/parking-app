package tk.parking.app.http.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import tk.parking.app.http.request.ParkingDTO;
import tk.parking.app.service.ParkingService;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/parking")
public class ParkingController {

    @Autowired
    private ParkingService parkingService;

    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Integer createParking(@Valid @RequestBody ParkingDTO parking) {
        log.info("Received a request");
        log.info(parking.toString());
        log.trace(parking.toString());
        return parkingService.createParking(parking);
    }
}
