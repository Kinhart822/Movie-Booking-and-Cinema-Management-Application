package vn.edu.usth.mcma.backend.entity;

import java.io.Serial;
import java.io.Serializable;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingSeatPK implements Serializable {
    @Serial private static final long serialVersionUID = 1L;

    @ManyToOne
    @JoinColumn(name = "booking_id")
    private Booking booking;

    @ManyToOne
    @JoinColumns({
        @JoinColumn(name = "screen_id", referencedColumnName = "screen_id"),
        @JoinColumn(name = "root_row", referencedColumnName = "row"),
        @JoinColumn(name = "root_col", referencedColumnName = "col")
    })
    private Seat seat;
}
