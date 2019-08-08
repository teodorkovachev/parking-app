package tk.parking.app.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@MappedSuperclass
public class CheckPoint extends ParkingLevelRefHolder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long checkpointId;

    private String address;
}
