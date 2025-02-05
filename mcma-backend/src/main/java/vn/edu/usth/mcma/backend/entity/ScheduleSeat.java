package vn.edu.usth.mcma.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Entity
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
}
