package vn.edu.usth.mcma.backend.dto.bookingsession;

import lombok.Data;

import java.util.List;

@Data
public class BookingPendingPayment {
    private List<BookingSeatRequest> seats;
    private List<BookingAudienceTypeRequest> audienceTypes;
    private List<BookingConcessionRequest> concessions;
}
