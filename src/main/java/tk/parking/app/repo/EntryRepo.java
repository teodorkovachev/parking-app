package tk.parking.app.repo;

import org.springframework.data.repository.CrudRepository;
import tk.parking.app.entity.ParkingEntry;

public interface EntryRepo extends CrudRepository<ParkingEntry, Integer> {
}
