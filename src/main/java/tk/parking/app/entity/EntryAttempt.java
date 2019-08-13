package tk.parking.app.entity;

import lombok.*;
import tk.parking.app.common.AttemptStatus;
import tk.parking.app.common.FailReason;
import tk.parking.app.common.VehicleType;

import javax.persistence.*;

@Entity
@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class EntryAttempt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer entryAttemptId;

    private Integer entryId;

    @Enumerated(EnumType.STRING)
    private VehicleType vehicleType;

    @Enumerated(EnumType.STRING)
    private AttemptStatus status;

    private String vehicleId;

    @Enumerated(EnumType.STRING)
    private FailReason reason;
}
