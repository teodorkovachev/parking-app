package tk.parking.app.repo;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tk.parking.app.common.VehicleType;
import tk.parking.app.entity.ParkingSpot;

import java.util.List;
import java.util.Optional;

public interface ParkingSpotRepository extends JpaRepository<ParkingSpot, Integer> {
    @Query("SELECT ps FROM ParkingSpot ps JOIN ps.parkingLevel.entries e where ps.vehicleType = ?1 AND e.entryId = ?2 AND ps.vehicleId IS NULL")
    List<ParkingSpot> findFreeParkingSpotsByTypeAndEntry(final VehicleType vehicleType, final int entryId, final Pageable pageable);

    Optional<ParkingSpot> findByVehicleId(final String vehicleId);
}
