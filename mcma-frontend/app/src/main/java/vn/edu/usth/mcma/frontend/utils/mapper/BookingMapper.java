package vn.edu.usth.mcma.frontend.utils.mapper;

import java.util.List;

import vn.edu.usth.mcma.frontend.model.Booking;
import vn.edu.usth.mcma.frontend.model.parcelable.AudienceTypeParcelable;
import vn.edu.usth.mcma.frontend.model.parcelable.ConcessionParcelable;
import vn.edu.usth.mcma.frontend.model.parcelable.SeatParcelable;
import vn.edu.usth.mcma.frontend.model.request.BookingAudienceTypeRequest;
import vn.edu.usth.mcma.frontend.model.request.BookingConcessionRequest;
import vn.edu.usth.mcma.frontend.model.request.BookingPendingPayment;
import vn.edu.usth.mcma.frontend.model.request.BookingSeatRequest;
import vn.edu.usth.mcma.frontend.utils.helper.SeatHelper;

public class BookingMapper {
    public static BookingPendingPayment fromBooking(Booking booking) {
        return BookingPendingPayment.builder()
                .seats(BookingMapper.fromBookingSeatParcelableList(booking.getSeats()))
                .audienceTypes(BookingMapper.fromBookingAudienceTypeParcelableList(booking.getAudienceTypes()))
                .concessions(BookingMapper.fromBookingConcessionParcelableList(booking.getConcessions()))
                .build();
    }
    /*
     * must be used with SeatHelper.getRootSeats()
     */
    public static BookingSeatRequest fromBookingSeatParcelable(SeatParcelable bookingParcelable) {
        return BookingSeatRequest.builder()
                .rootRow(bookingParcelable.getRootRow())
                .rootCol(bookingParcelable.getRootCol()).build();
    }
    public static List<BookingSeatRequest> fromBookingSeatParcelableList(List<SeatParcelable> bookingParcelables) {
        return SeatHelper.getRootSeats(bookingParcelables).stream()
                .map(BookingMapper::fromBookingSeatParcelable)
                .toList();
    }
    public static BookingAudienceTypeRequest fromBookingAudienceTypeParcelable(AudienceTypeParcelable bookingParcelable) {
        return BookingAudienceTypeRequest.builder()
                .id(bookingParcelable.getId())
                .quantity(bookingParcelable.getQuantity()).build();
    }
    public static List<BookingAudienceTypeRequest> fromBookingAudienceTypeParcelableList(List<AudienceTypeParcelable> bookingParcelables) {
        return bookingParcelables.stream()
                .map(BookingMapper::fromBookingAudienceTypeParcelable)
                .toList();
    }
    public static BookingConcessionRequest fromBookingConcessionParcelable(ConcessionParcelable bookingParcelable) {
        return BookingConcessionRequest.builder()
                .id(bookingParcelable.getId())
                .quantity(bookingParcelable.getQuantity()).build();
    }
    public static List<BookingConcessionRequest> fromBookingConcessionParcelableList(List<ConcessionParcelable> bookingParcelables) {
        return bookingParcelables.stream()
                .map(BookingMapper::fromBookingConcessionParcelable)
                .toList();
    }
}
