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
public class Parking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer parkingId;

    private String parkingName;

    @OneToMany(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "parking_id")
    private List<ParkingLevel> parkingLevels;
}
