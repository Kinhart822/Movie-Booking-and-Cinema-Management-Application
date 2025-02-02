package vn.edu.usth.mcma.backend.controller.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.edu.usth.mcma.backend.dto.booking.ScheduleDetail;
import vn.edu.usth.mcma.backend.entity.Audience;
import vn.edu.usth.mcma.backend.service.BookingService;

import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user/booking")
public class BookingController {
    private final BookingService bookingService;

    @GetMapping("/schedule/{scheduleId}")
    public ResponseEntity<ScheduleDetail> findScheduleDetail(@PathVariable Long scheduleId) {
        return ResponseEntity.ok(bookingService.findScheduleDetail(scheduleId));
    }
    @GetMapping("/rating/{ratingId}/audience-type")
    public ResponseEntity<Set<Audience>> findAllAudienceTypeByRating(@PathVariable String ratingId) {
        return ResponseEntity.ok(bookingService.findAllAudienceTypeByRating(ratingId));
    }
}
