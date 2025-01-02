package com.spring.service.impl;

import com.spring.entities.Booking;
import com.spring.entities.BookingSeat;
import com.spring.entities.Seat;
import com.spring.enums.SeatStatus;
import com.spring.repository.BookingRepository;
import com.spring.repository.SeatRepository;
import com.spring.service.BookingCleanUpScheduleService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BookingCleanUpScheduleServiceImpl implements BookingCleanUpScheduleService {
    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private SeatRepository seatRepository;

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
}
