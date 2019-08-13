package tk.parking.app.entity;

import lombok.*;
import tk.parking.app.common.AttemptStatus;
import tk.parking.app.common.FailReason;

import javax.persistence.*;

@Entity
@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class ExitAttempt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer exitAttemptId;

    private Integer exitId;

    @Enumerated(EnumType.STRING)
    private AttemptStatus status;

    private String vehicleId;

    @Enumerated(EnumType.STRING)
    private FailReason reason;
}
