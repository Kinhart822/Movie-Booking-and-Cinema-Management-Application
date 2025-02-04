package vn.edu.usth.mcma.backend.controller.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.edu.usth.mcma.backend.dto.SeatPresentation;
import vn.edu.usth.mcma.backend.dto.SeatTypePresentation;
import vn.edu.usth.mcma.backend.dto.booking.AudienceDetail;
import vn.edu.usth.mcma.backend.dto.booking.ConcessionDetail;
import vn.edu.usth.mcma.backend.dto.booking.ScheduleDetail;
import vn.edu.usth.mcma.backend.entity.Concession;
import vn.edu.usth.mcma.backend.service.BookingService;
import vn.edu.usth.mcma.backend.service.SeatService;

import java.util.List;
import java.util.Set;

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
}
