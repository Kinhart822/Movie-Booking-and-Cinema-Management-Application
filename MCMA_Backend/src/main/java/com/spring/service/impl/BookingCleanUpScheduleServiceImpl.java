package com.spring.service.impl;

import com.spring.entities.*;
import com.spring.enums.BookingStatus;
import com.spring.enums.PaymentMethod;
import com.spring.enums.SeatStatus;
import com.spring.repository.BookingRepository;
import com.spring.repository.BookingTicketRepository;
import com.spring.repository.NotificationRepository;
import com.spring.repository.SeatRepository;
import com.spring.service.BookingCleanUpScheduleService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class BookingCleanUpScheduleServiceImpl implements BookingCleanUpScheduleService {
    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private BookingTicketRepository bookingTicketRepository;

    @Autowired
    private NotificationRepository notificationRepository;

    // <giây> <phút> <giờ> <ngày> <tháng> <ngày trong tuần>
    //      * : bất kì giá trị nào
    //      , : danh sách các giá trị
    //      - : khoảng giá trị
    //      / : theo bước nhảy
    //      0-59: đối với các giá trị giây, phút
    //      0-23: đối với giá trị giờ
    //      1-31: đối với giá trị ngày
    //      1-12: đối với giá trị tháng73
    //      JAN-DEC: đối với giá trị tháng``
    //      0-6: đối với ngày trong tuần585675678
    //      SUN-SAT: đối với ngày tro678576ng tuần
    @Transactional
    @Scheduled(cron = "0 */5 * * * *")
    @Override
    public void releaseSeatsForAfterMovieEnds() {
        System.out.println("Đang kiểm tra các booking đã hết hạn...");

        List<Booking> bookingsThatHaveUsersWatchedMovies = bookingRepository.findByEndDateTimeBefore(LocalDateTime.now());
        if (bookingsThatHaveUsersWatchedMovies.isEmpty()) {
            System.out.println("Không có booking nào cần giải phóng ghế.");
            return;
        }

        bookingsThatHaveUsersWatchedMovies.forEach(booking -> {
            List<Seat> seatsToUpdate = booking.getSeatList().stream()
                    .map(BookingSeat::getSeat)
                    .filter(seat -> seat.getSeatStatus() == SeatStatus.Unavailable)
                    .peek(seat -> seat.setSeatStatus(SeatStatus.Available))
                    .toList();

            seatRepository.saveAll(seatsToUpdate);
        });

        System.out.println("Đã giải phóng các ghế cho các booking đã hết hạn.");
    }

    @Transactional
    @Scheduled(cron = "0 */5 * * * *")
    @Override
    public void holdBookingTemporarily() {
        List<Booking> existingBooking = bookingRepository.findByPaymentMethod(PaymentMethod.Cash);

        for (Booking bookingPayByCash : existingBooking) {
            if (bookingPayByCash.getStatus() == BookingStatus.Pending_Payment) {
                // Check if the current time is more than 10 minutes past the start time of the booking
                Duration duration = Duration.between(LocalDateTime.now(), bookingPayByCash.getStartDateTime());
                if (duration.toMinutes() > 10) {

                    // Release held seats
                    List<Seat> heldSeats = bookingPayByCash.getSeatList().stream()
                            .map(BookingSeat::getSeat)
                            .toList();
                    heldSeats.forEach(seat -> seat.setSeatStatus(SeatStatus.Available));
                    seatRepository.saveAll(heldSeats);

                    // Delete associated tickets
                    for (BookingTicket bookingTicket : bookingPayByCash.getTickets()) {
                        bookingTicket.setTicket(null);
                        bookingTicketRepository.delete(bookingTicket);
                    }

                    // Delete the booking
                    bookingRepository.delete(bookingPayByCash);

                    System.out.printf("Booking has been canceled for booking ID: %d due to incomplete payment before the movie start date time begins!%n", bookingPayByCash.getId());

                    // Send notification to the user
                    Notification notification = new Notification();
                    notification.setUser(bookingPayByCash.getUser());
                    notification.setMessage("Your booking has been canceled because the booking was not completed before the movie start date time.");
                    notification.setDateCreated(LocalDateTime.now());
                    notificationRepository.save(notification);
                }
            }
        }

        System.out.println("Đã giải phóng các booking mà chưa trả tiền trước khi movie bắt đầu.");
    }
}
