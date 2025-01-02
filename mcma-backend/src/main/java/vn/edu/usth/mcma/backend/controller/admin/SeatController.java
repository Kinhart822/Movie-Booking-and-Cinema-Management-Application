package vn.edu.usth.mcma.backend.controller.admin;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import vn.edu.usth.mcma.backend.dto.SeatMapRequest;
import vn.edu.usth.mcma.backend.dto.SeatMapResponse;
import vn.edu.usth.mcma.backend.entity.Seat;
import vn.edu.usth.mcma.backend.exception.ApiResponse;
import vn.edu.usth.mcma.backend.service.SeatService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class SeatController {
    private final SeatService seatService;
    @PostMapping("/seat")
    public ApiResponse createSeatMap(@Valid @RequestBody SeatMapRequest request) {
        return seatService.createSeatMap(request);
    }
    @GetMapping("/seat/{screenId}")
    public SeatMapResponse findSeatMapByScreenId(@PathVariable Long screenId) {
        return seatService.findSeatMapByScreenId(screenId);
    }
//    @PutMapping("/seat/{id}")
//    public ApiResponse updateSeat(@PathVariable Long id, @RequestBody SeatMapRequest request) {
//        return seatService.updateSeat(id, request);
//    }
//    @DeleteMapping("/seat/{id}")
//    public ApiResponse deleteSeat(@PathVariable Long id) {
//        return seatService.deleteSeat(id);
//    }
}
