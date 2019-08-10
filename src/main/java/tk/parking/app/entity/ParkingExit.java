package tk.parking.app.entity;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
@Builder
public class ParkingExit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer exitId;

    private String address;
}
