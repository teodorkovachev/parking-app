package tk.parking.app.entity;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Builder
public class ParkingLevel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long levelId;

    private Integer level;

    @OneToMany(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "level_id")
    private List<ParkingEntry> entries;

    @OneToMany(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "level_id")
    private List<ParkingExit> parkingExits;

    @OneToMany(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "level_id")
    private List<ParkingSpot> parkingSpots;
}
