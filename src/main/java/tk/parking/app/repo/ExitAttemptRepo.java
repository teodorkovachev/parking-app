package tk.parking.app.repo;

import org.springframework.data.repository.CrudRepository;
import tk.parking.app.entity.ExitAttempt;

public interface ExitAttemptRepo extends CrudRepository<ExitAttempt, Integer> {
}
