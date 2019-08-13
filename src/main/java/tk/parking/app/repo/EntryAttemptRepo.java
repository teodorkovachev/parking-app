package tk.parking.app.repo;

import org.springframework.data.repository.CrudRepository;
import tk.parking.app.entity.EntryAttempt;

public interface EntryAttemptRepo extends CrudRepository<EntryAttempt, Integer> {
}
