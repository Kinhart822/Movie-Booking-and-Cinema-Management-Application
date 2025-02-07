package vn.edu.usth.mcma.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;

@Entity
@Table(name = "map_schedule_seat")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScheduleSeat implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    private ScheduleSeatPK id;
    @Column
    private String seatAvailability;
    @Column
    private Instant holdUntil;
}
