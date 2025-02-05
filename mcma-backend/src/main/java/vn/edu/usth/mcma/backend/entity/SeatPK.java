package vn.edu.usth.mcma.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SeatPK implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Column
    private Long screenId;
    @Column(columnDefinition = "TINYINT UNSIGNED")
    private Integer row;
    @Column(columnDefinition = "TINYINT UNSIGNED")
    private Integer col;

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof SeatPK seatPK)) return false;
        return Objects.equals(getScreenId(), seatPK.getScreenId()) && Objects.equals(getRow(), seatPK.getRow()) && Objects.equals(getCol(), seatPK.getCol());
    }
    @Override
    public int hashCode() {
        return Objects.hash(getScreenId(), getRow(), getCol());
    }
}
