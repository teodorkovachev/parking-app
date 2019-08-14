package tk.parking.app.http.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tk.parking.app.entity.ParkingSpot;
import tk.parking.app.exception.SegmentNotFoundException;
import tk.parking.app.http.response.NotFoundErrResponse;
import tk.parking.app.service.ParkingSpotService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/spots")
public class ParkingSpotsController {

    @Autowired
    private ParkingSpotService parkingSpotService;

    @RequestMapping(method = RequestMethod.GET, path = "/{parkingId}")
    public List<ParkingSpot> getParkingSpots(@PathVariable int parkingId, @RequestParam(required = false) Integer level) {
        log.trace("Requested all available parking spots. Parking Id: {}, Level: {}", parkingId, level);
        return parkingSpotService.getAllParkingSpots(parkingId, level);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(SegmentNotFoundException.class)
    public NotFoundErrResponse handleSegmentNotFound(final SegmentNotFoundException e) {
        return NotFoundErrResponse.builder().notFound(e.segment).build();
    }
}
