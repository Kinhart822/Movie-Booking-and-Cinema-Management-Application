package vn.edu.usth.mcma.frontend.model.request;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookingPendingPayment {
    private List<BookingSeatRequest> seats;
    private List<BookingAudienceTypeRequest> audienceTypes;
    private List<BookingConcessionRequest> concessions;
}
