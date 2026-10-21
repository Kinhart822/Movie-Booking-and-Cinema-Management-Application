package vn.edu.usth.mcma.backend.dto.request.booking;

import java.util.List;

import lombok.Data;

@Data
public class BookingPendingPayment {
    private List<BookingSeatRequest> seats;
    private List<BookingAudienceTypeRequest> audienceTypes;
    private List<BookingConcessionRequest> concessions;
}
