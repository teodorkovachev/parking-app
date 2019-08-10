package tk.parking.app.entity;

import lombok.Builder;
import lombok.Data;
import tk.parking.app.common.VehicleType;

import javax.persistence.*;

@Entity
@Data
@Builder
public class ParkingSpot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long parkingSpotId;

    @Enumerated(EnumType.STRING)
    private VehicleType vehicleType;
}
