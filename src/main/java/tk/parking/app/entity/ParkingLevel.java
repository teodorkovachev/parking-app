package tk.parking.app.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class ParkingLevel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer levelId;

    @Column(name = "parking_id")
    private Integer parkingId;

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
