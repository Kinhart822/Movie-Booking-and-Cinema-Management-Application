package vn.edu.usth.mcma.backend.domain;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import vn.edu.usth.mcma.backend.entity.Concession;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class BookingConcessionPK {
    @ManyToOne
    @JoinColumn(name = "booking_id")
    private Booking booking;
    @ManyToOne
    @JoinColumn(name = "concession_id")
    private Concession concession;
}
