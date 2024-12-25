package vn.edu.usth.mcma.backend.controller.admin;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import vn.edu.usth.mcma.backend.dto.SeatMapRequest;
import vn.edu.usth.mcma.backend.exception.ApiResponse;
import vn.edu.usth.mcma.backend.service.SeatService;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class SeatController {
    private final SeatService seatService;
    @PostMapping("/seat")
    public ApiResponse createSeat(@Valid @RequestBody SeatMapRequest request) {
        return seatService.createSeatMap(request);
    }
//    @GetMapping("/seat")
//    public List<Seat> findAll(@RequestParam(required = false, defaultValue = "") String query, @PageableDefault Pageable pageable) {
//        return seatService.findAll(query, pageable);
//    }
//    @GetMapping("/seat/{id}")
//    public Seat findById(@PathVariable Long id) {
//        return seatService.findById(id);
//    }
//    @PutMapping("/seat/{id}")
//    public ApiResponse updateSeat(@PathVariable Long id, @RequestBody SeatMapRequest request) {
//        return seatService.updateSeat(id, request);
//    }
//    @DeleteMapping("/seat/{id}")
//    public ApiResponse deleteSeat(@PathVariable Long id) {
//        return seatService.deleteSeat(id);
//    }
}
