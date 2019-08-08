package tk.parking.app.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class ParkingLevel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long level;

    @ManyToOne
    @JoinColumn(name = "parking_id")
    private Parking parking;

    @OneToMany(mappedBy = "parkingLevel")
    private List<ParkingEntry> entries;

    @OneToMany(mappedBy = "parkingLevel")
    private List<ParkingExit> parkingExits;

    @OneToMany(mappedBy = "parkingLevel")
    private List<ParkingSpot> parkingSpots;
}
