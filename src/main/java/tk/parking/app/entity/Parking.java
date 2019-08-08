package tk.parking.app.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Parking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long parkingId;

    private String parkingName;

    @OneToMany(mappedBy = "parking")
    private List<ParkingLevel> parkingLevels;
}
