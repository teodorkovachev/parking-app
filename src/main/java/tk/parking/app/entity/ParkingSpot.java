package tk.parking.app.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import tk.parking.app.common.VehicleType;

import javax.persistence.*;

@Entity
@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class ParkingSpot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer parkingSpotId;

    @Enumerated(EnumType.STRING)
    private VehicleType vehicleType;

    @JsonIgnore
    private String vehicleId;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "level_id")
    private ParkingLevel parkingLevel;

    @Version
    @JsonIgnore
    private long version;
}
