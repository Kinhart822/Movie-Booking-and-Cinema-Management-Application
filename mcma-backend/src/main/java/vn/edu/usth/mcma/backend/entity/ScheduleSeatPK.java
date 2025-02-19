package vn.edu.usth.mcma.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.edu.usth.mcma.backend.domain.Booking;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScheduleSeatPK implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @ManyToOne
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;
    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "screen_id", referencedColumnName = "screen_id"),
            @JoinColumn(name = "root_row", referencedColumnName = "row"),
            @JoinColumn(name = "root_col", referencedColumnName = "col")
    })
    private Seat seat;
}
