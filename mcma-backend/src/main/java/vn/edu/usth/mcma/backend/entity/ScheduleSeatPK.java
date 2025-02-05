package vn.edu.usth.mcma.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    @Column
    private Long scheduleId;
    @Column
    private SeatPK seatId;

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ScheduleSeatPK that)) return false;
        return Objects.equals(getScheduleId(), that.getScheduleId()) && getSeatId().equals(that.getSeatId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getScheduleId(), getSeatId());
    }
}
