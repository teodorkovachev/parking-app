package tk.parking.app.repo;

import org.springframework.data.repository.CrudRepository;
import tk.parking.app.entity.ParkingExit;

public interface ExitRepo extends CrudRepository<ParkingExit, Integer> {
}
