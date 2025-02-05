package vn.edu.usth.mcma.backend.controller.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.usth.mcma.backend.dto.SeatPresentation;
import vn.edu.usth.mcma.backend.dto.SeatTypePresentation;
import vn.edu.usth.mcma.backend.dto.bookingsession.*;
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
    @GetMapping("/rating/{ratingId}/audience-type")//todo /schedule/{scheduleId}/audience-type
    public ResponseEntity<List<AudienceDetail>> findAllAudienceTypeByRating(@PathVariable String ratingId) {
        return ResponseEntity.ok(bookingService.findAllAudienceTypeByRating(ratingId));
    }
    @GetMapping("/seat-types")//todo /schedule/{scheduleId}/seat-types
    public ResponseEntity<List<SeatTypePresentation>> findAllSeatTypes() {
        return ResponseEntity.ok(seatService.findAllSeatTypes());
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

}
