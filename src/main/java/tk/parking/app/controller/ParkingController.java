package tk.parking.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import tk.parking.app.entity.Parking;
import tk.parking.app.repo.ParkingRepo;

@RestController
@RequestMapping("/parking")
public class ParkingController {

    @Autowired
    private ParkingRepo parkingRepo;

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Long createParking(@RequestBody Parking parking) {
        return parkingRepo.save(parking).getParkingId();
    }
}
