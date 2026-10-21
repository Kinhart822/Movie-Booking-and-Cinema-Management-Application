package vn.edu.usth.mcma.backend.module.cinema.entity;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import lombok.*;

@Entity
@Table(name = "map_schedule_seat")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class ScheduleSeat {
    @EmbeddedId private ScheduleSeatPK id;
    @Column private String seatAvailability;
    @Column private Instant holdUntil;
}
