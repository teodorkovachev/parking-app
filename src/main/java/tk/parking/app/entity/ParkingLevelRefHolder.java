package tk.parking.app.entity;

import lombok.Data;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

@Data
@MappedSuperclass
public class ParkingLevelRefHolder {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "level_id")
    private ParkingLevel parkingLevel;
}
