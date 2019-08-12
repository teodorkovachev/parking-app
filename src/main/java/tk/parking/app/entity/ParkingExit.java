package tk.parking.app.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class ParkingExit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer exitId;

    private String address;
}
