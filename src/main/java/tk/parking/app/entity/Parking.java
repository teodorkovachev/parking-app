package tk.parking.app.entity;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Builder
public class Parking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer parkingId;

    private String parkingName;

    @OneToMany(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "parking_id")
    private List<ParkingLevel> parkingLevels;
}
