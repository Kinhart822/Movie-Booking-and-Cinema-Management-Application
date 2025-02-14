package vn.edu.usth.mcma.backend.controller.admin;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.usth.mcma.backend.dto.admin.dto.cinema.FilterCinema;
import vn.edu.usth.mcma.backend.dto.admin.dto.cinema.FilterScreen;
import vn.edu.usth.mcma.backend.dto.cinema.CinemaProjection;
import vn.edu.usth.mcma.backend.dto.cinema.CinemaRequest;
import vn.edu.usth.mcma.backend.dto.cinema.ScheduleOfScreenResponse;
import vn.edu.usth.mcma.backend.dto.movie.MovieScheduleRequest;
import vn.edu.usth.mcma.backend.dto.unsorted.ScreenProjection;
import vn.edu.usth.mcma.backend.dto.unsorted.ScreenRequest;
import vn.edu.usth.mcma.backend.dto.unsorted.SeatHelperInput;
import vn.edu.usth.mcma.backend.dto.unsorted.SeatResponse;
import vn.edu.usth.mcma.backend.entity.Screen;
import vn.edu.usth.mcma.backend.exception.ApiResponse;
import vn.edu.usth.mcma.backend.repository.ScreenRepository;
import vn.edu.usth.mcma.backend.service.CinemaService;
import vn.edu.usth.mcma.backend.entity.Cinema;
import vn.edu.usth.mcma.backend.service.MovieService;
import vn.edu.usth.mcma.backend.service.ScreenService;
import vn.edu.usth.mcma.backend.service.SeatService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class CinemaController {
    private final CinemaService cinemaService;
    private final ScreenService screenService;
    private final ScreenRepository screenRepository;
    private final SeatService seatService;
    private final MovieService movieService;

    /*
     * ==========
     * Cinema
     * ==========
     */
    @PostMapping("/cinema")
    public ResponseEntity<ApiResponse> createCinema(@RequestBody CinemaRequest request) {
        return ResponseEntity.ok(cinemaService.createCinema(request));
    }
    @GetMapping("/cinema")
    public ResponseEntity<List<CinemaProjection>> findAllCinema(@RequestParam(required = false, defaultValue = "") String query) {
        return ResponseEntity.ok(cinemaService.findAll(query));
    }
    @GetMapping("/cinema/{id}")
    public ResponseEntity<Cinema> findCinemaById(@PathVariable Long id) {
        return ResponseEntity.ok(cinemaService.findById(id));
    }
    @PutMapping("/cinema/{cinemaId}")
    public ResponseEntity<ApiResponse> updateCinema(@PathVariable Long cinemaId, @RequestBody CinemaRequest request) {
        return ResponseEntity.ok(cinemaService.updateCinema(cinemaId, request));
    }
    @PatchMapping("/cinema/{cinemaId}")
    public ResponseEntity<ApiResponse> toggleCinemaStatus(@PathVariable Long cinemaId) {
        return ResponseEntity.ok(cinemaService.toggleStatus(cinemaId));
    }
    @DeleteMapping("/cinema")
    public ResponseEntity<ApiResponse> deactivateCinemas(@RequestBody List<Long> ids) {
        return ResponseEntity.ok(cinemaService.deactivateCinemas(ids));
    }
    /*
     * ==========
     * Screen
     * ==========
     */
    @PostMapping("/cinema/{cinemaId}/screen")
    public ResponseEntity<ApiResponse> createScreen(@PathVariable Long cinemaId, @RequestBody ScreenRequest request) {
        return ResponseEntity.ok(screenService.createScreen(cinemaId, request));
    }
    @GetMapping("/cinema/{cinemaId}/screen")
    public ResponseEntity<List<ScreenProjection>> findAllByCinemaId(@PathVariable Long cinemaId, @RequestParam(required = false, defaultValue = "") String query) {
        return ResponseEntity.ok(screenService.findAllProjectionByQuery(cinemaId, query));
    }
    @GetMapping("/cinema/screen/{screenId}")
    public ResponseEntity<Screen> findScreenById(@PathVariable Long screenId) {
        return ResponseEntity.ok(screenService.findById(screenId));
    }
    @PutMapping("/cinema/screen/{screenId}")
    public ResponseEntity<ApiResponse> updateScreen(@PathVariable Long screenId, @RequestBody ScreenRequest request) {
        return ResponseEntity.ok(screenService.updateScreen(screenId, request));
    }
    @PatchMapping("/cinema/screen/{screenId}")
    public ResponseEntity<ApiResponse> toggleScreenStatus(@PathVariable Long screenId) {
        return ResponseEntity.ok(screenService.toggleStatus(screenId));
    }
    @DeleteMapping("/cinema/screen")
    public ResponseEntity<ApiResponse> deactivateScreens(@RequestBody List<Long> ids) {
        return ResponseEntity.ok(screenService.deactivateScreens(ids));
    }
    /*
      ==========
      Seat
      ==========
     */
    @GetMapping("/screen/{screenId}/seat")
    public ResponseEntity<List<SeatResponse>> findSeatMapByScreenId(@PathVariable Long screenId) {
        return ResponseEntity.ok(seatService.findSeatMapByScreenId(screenId));
    }
    @PostMapping("/screen/{screenId}/seat")
    public ResponseEntity<ApiResponse> initSeatMap(@PathVariable Long screenId, @Valid @RequestBody List<SeatHelperInput> seatHelperInputs) {
        return ResponseEntity.ok(seatService.initSeatMap(screenId, seatHelperInputs));
    }
    @PutMapping("/screen/{screenId}/seat")
    public ResponseEntity<ApiResponse> updateSeatMap(@PathVariable Long screenId, @Valid @RequestBody List<SeatHelperInput> seatHelperInputs) {
        return ResponseEntity.ok(seatService.updateSeatMap(screenId, seatHelperInputs));
    }
    /*
     * ========
     * schedule
     * ========
     */
    @PostMapping("/cinema/schedule")
    public ResponseEntity<ApiResponse> addSchedules(@RequestBody List<MovieScheduleRequest> requests) {
        return ResponseEntity.ok(movieService.addSchedules(requests));
    }
    @Operation(summary = "Find all schedule by cinema")
    @GetMapping("/cinema/{cinemaId}/schedule")
    public ResponseEntity<List<ScheduleOfScreenResponse>> findAllScheduleByCinema(@PathVariable Long cinemaId) {
        return ResponseEntity.ok(movieService.findAllScheduleByCinema(cinemaId));
    }



    /*
     * unsorted
     */
    @GetMapping("/cinema/filter")
    public ResponseEntity<List<FilterCinema>> filterByCinema() {
        return ResponseEntity.ok(cinemaService.filterByCinema());
    }
    @GetMapping("/cinema/{cinemaId}/screen/filter")
    public ResponseEntity<List<FilterScreen>> filterByScreen(@PathVariable Long cinemaId) {
        return ResponseEntity.ok(cinemaService.filterByScreen(cinemaId));
    }
}
