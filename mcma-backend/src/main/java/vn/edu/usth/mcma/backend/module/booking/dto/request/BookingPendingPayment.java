package vn.edu.usth.mcma.backend.module.booking.dto.request;

import java.util.List;

import lombok.Data;

@Data
public class BookingPendingPayment {
    private List<BookingSeatRequest> seats;
    private List<BookingAudienceTypeRequest> audienceTypes;
    private List<BookingConcessionRequest> concessions;
}
