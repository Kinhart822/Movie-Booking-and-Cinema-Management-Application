package vn.edu.usth.mcma.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
public class SeatPK implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @ManyToOne
    @JoinColumn(name = "screen_id")
    private Screen screen;
    @Column(columnDefinition = "TINYINT UNSIGNED")
    private Integer row;
    @Column(columnDefinition = "TINYINT UNSIGNED")
    private Integer col;

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof SeatPK seatPK)) return false;
        return Objects.equals(getScreen().getId(), seatPK.getScreen().getId()) && Objects.equals(getRow(), seatPK.getRow()) && Objects.equals(getCol(), seatPK.getCol());
    }
    @Override
    public int hashCode() {
        return Objects.hash(getScreen().getId(), getRow(), getCol());
    }
}
