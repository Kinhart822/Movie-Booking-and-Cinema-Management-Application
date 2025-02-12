package vn.edu.usth.mcma.backend.controller.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.usth.mcma.backend.dto.SeatPresentation;
import vn.edu.usth.mcma.backend.dto.SeatTypeResponse;
import vn.edu.usth.mcma.backend.dto.bookingsession.*;
import vn.edu.usth.mcma.backend.entity.PaymentMethod;
import vn.edu.usth.mcma.backend.exception.ApiResponse;
import vn.edu.usth.mcma.backend.service.BookingService;
import vn.edu.usth.mcma.backend.service.SeatService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user/booking")
public class BookingController {
    private final BookingService bookingService;
    private final SeatService seatService;

    @GetMapping("/schedule/{scheduleId}")
    public ResponseEntity<ScheduleDetail> findScheduleDetail(@PathVariable Long scheduleId) {
        return ResponseEntity.ok(bookingService.findScheduleDetail(scheduleId));
    }
    @GetMapping("/schedule/{scheduleId}/audience-type")
    public ResponseEntity<List<AudienceDetail>> findAllAudienceTypeBySchedule(@PathVariable Long scheduleId) {
        return ResponseEntity.ok(bookingService.findAllAudienceTypeBySchedule(scheduleId));
    }
    @GetMapping("/schedule/{scheduleId}/seat-types")
    public ResponseEntity<List<SeatTypeResponse>> findAllSeatTypeBySchedule(@PathVariable Long scheduleId) {
        return ResponseEntity.ok(bookingService.findAllSeatTypeBySchedule(scheduleId));
    }
    @GetMapping("/schedule/{scheduleId}/seat")
    public ResponseEntity<List<SeatPresentation>> findAllSeatBySchedule(@PathVariable Long scheduleId) {
        return ResponseEntity.ok(bookingService.findAllSeatBySchedule(scheduleId));
    }
    @GetMapping("/schedule/{scheduleId}/concession")
    public ResponseEntity<List<ConcessionDetail>> findAllConcessionBySchedule(@PathVariable Long scheduleId) {
        return ResponseEntity.ok(bookingService.findAllConcessionBySchedule(scheduleId));
    }
    @PostMapping("/register-booking-session")
    public ResponseEntity<ApiResponse> registerBookingSession(@RequestBody SessionRegistration request) {
        return ResponseEntity.ok(bookingService.registerBookingSession(request));
    }
    @PutMapping("/schedule/{scheduleId}/seat/hold-request")
    public ResponseEntity<ApiResponse> holdSeatRequest(@PathVariable Long scheduleId, @RequestBody HoldSeatRequest request) {
        return ResponseEntity.ok(bookingService.holdSeatRequest(scheduleId, request));
    }
    @GetMapping("/payment-method")
    public ResponseEntity<List<PaymentMethod>> findAllPaymentMethod() {
        return ResponseEntity.ok(bookingService.findAllPaymentMethod());
    }
}
