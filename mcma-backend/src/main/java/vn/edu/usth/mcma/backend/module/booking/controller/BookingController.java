package vn.edu.usth.mcma.backend.module.booking.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import vn.edu.usth.mcma.backend.common.dto.ApiResponse;
import vn.edu.usth.mcma.backend.module.booking.dto.request.*;
import vn.edu.usth.mcma.backend.module.booking.dto.response.*;
import vn.edu.usth.mcma.backend.module.booking.entity.PaymentMethod;
import vn.edu.usth.mcma.backend.module.booking.service.BookingService;
import vn.edu.usth.mcma.backend.module.cinema.dto.response.SeatPresentation;
import vn.edu.usth.mcma.backend.module.cinema.dto.response.SeatTypeResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user/booking")
public class BookingController {
    private final BookingService bookingService;

    @GetMapping("/schedule/{scheduleId}")
    public ResponseEntity<ScheduleDetail> findScheduleDetail(
            @PathVariable @NonNull Long scheduleId) {
        return ResponseEntity.ok(bookingService.findScheduleDetail(scheduleId));
    }

    @GetMapping("/schedule/{scheduleId}/audience-type")
    public ResponseEntity<List<AudienceDetail>> findAllAudienceTypeBySchedule(
            @PathVariable @NonNull Long scheduleId) {
        return ResponseEntity.ok(bookingService.findAllAudienceTypeBySchedule(scheduleId));
    }

    @GetMapping("/schedule/{scheduleId}/seat-types")
    public ResponseEntity<List<SeatTypeResponse>> findAllSeatTypeBySchedule(
            @PathVariable @NonNull Long scheduleId) {
        return ResponseEntity.ok(bookingService.findAllSeatTypeBySchedule(scheduleId));
    }

    @GetMapping("/schedule/{scheduleId}/seat")
    public ResponseEntity<List<SeatPresentation>> findAllSeatBySchedule(
            @PathVariable @NonNull Long scheduleId) {
        return ResponseEntity.ok(bookingService.findAllSeatBySchedule(scheduleId));
    }

    @GetMapping("/schedule/{scheduleId}/concession")
    public ResponseEntity<List<ConcessionDetail>> findAllConcessionBySchedule(
            @PathVariable @NonNull Long scheduleId) {
        return ResponseEntity.ok(bookingService.findAllConcessionBySchedule(scheduleId));
    }

    @PostMapping("/register-booking-session")
    public ResponseEntity<Long> registerBookingSession(@RequestBody @NonNull Long scheduleId) {
        return ResponseEntity.ok(bookingService.registerBookingSession(scheduleId));
    }

    @PutMapping("/{bookingId}/hold-request")
    public ResponseEntity<ApiResponse> holdSeatRequest(
            @PathVariable @NonNull Long bookingId, @RequestBody HoldSeatRequest request) {
        return ResponseEntity.ok(bookingService.holdSeatRequest(bookingId, request));
    }

    @GetMapping("/payment-method")
    public ResponseEntity<List<PaymentMethod>> findAllPaymentMethod() {
        return ResponseEntity.ok(bookingService.findAllPaymentMethod());
    }

    @PostMapping("/{bookingId}/pending-payment")
    public ResponseEntity<BankTransferForm> pendingPayment(
            @PathVariable @NonNull Long bookingId, @RequestBody BookingPendingPayment request) {
        return ResponseEntity.ok(bookingService.pendingPayment(bookingId, request));
    }

    @GetMapping("/{bookingId}/finish")
    public ResponseEntity<Boolean> finishBooking(@PathVariable @NonNull Long bookingId) {
        return ResponseEntity.ok(bookingService.finishBooking(bookingId));
    }
}
