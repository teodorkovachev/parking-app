package tk.parking.app.repo;

import org.springframework.data.repository.CrudRepository;
import tk.parking.app.entity.Parking;

public interface ParkingRepo extends CrudRepository<Parking, Long> {
}
