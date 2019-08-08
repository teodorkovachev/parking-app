package tk.parking.app.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class ParkingSpot extends ParkingLevelRefHolder{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long parkingSpotId;

    private String vehicleType;
}
