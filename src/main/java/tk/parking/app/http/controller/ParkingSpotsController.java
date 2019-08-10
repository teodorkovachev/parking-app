package tk.parking.app.http.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import tk.parking.app.entity.ParkingSpot;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/spots")
public class ParkingSpotsController {
    @RequestMapping(method = RequestMethod.GET, path = "/{parkingId}")
    public List<ParkingSpot> getParkingSpots(@PathVariable int parkingId, @RequestParam(required = false) Integer level, @RequestParam(required = false) Boolean available) {
        log.trace("Requested all available parking spots. Parking Id: {}, Level: {}, Available: {}", parkingId, level, available);
        return new ArrayList<>();
    }
}
