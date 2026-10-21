package vn.edu.usth.mcma.backend.controller.admin;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import vn.edu.usth.mcma.backend.dto.request.cinema.FilterCinema;
import vn.edu.usth.mcma.backend.dto.request.cinema.FilterScreen;
import vn.edu.usth.mcma.backend.projection.CinemaProjection;
import vn.edu.usth.mcma.backend.dto.request.cinema.CinemaRequest;
import vn.edu.usth.mcma.backend.dto.response.cinema.ScheduleOfScreenResponse;
import vn.edu.usth.mcma.backend.dto.request.movie.MovieScheduleRequest;
import vn.edu.usth.mcma.backend.projection.ScreenProjection;
import vn.edu.usth.mcma.backend.dto.request.cinema.ScreenRequest;
import vn.edu.usth.mcma.backend.dto.response.misc.SeatHelperInput;
import vn.edu.usth.mcma.backend.dto.response.cinema.SeatResponse;
import vn.edu.usth.mcma.backend.entity.Cinema;
import vn.edu.usth.mcma.backend.entity.Screen;
import vn.edu.usth.mcma.backend.common.dto.ApiResponse;
import vn.edu.usth.mcma.backend.service.CinemaService;
import vn.edu.usth.mcma.backend.service.MovieService;
import vn.edu.usth.mcma.backend.service.ScreenService;
import vn.edu.usth.mcma.backend.service.SeatService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class CinemaController {
    private final CinemaService cinemaService;
    private final ScreenService screenService;
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
    public ResponseEntity<List<CinemaProjection>> findAllCinema(
            @RequestParam(required = false, defaultValue = "") String query) {
        return ResponseEntity.ok(cinemaService.findAll(query));
    }

    @GetMapping("/cinema/{id}")
    public ResponseEntity<Cinema> findCinemaById(@PathVariable @NonNull Long id) {
        return ResponseEntity.ok(cinemaService.findById(id));
    }

    @PutMapping("/cinema/{cinemaId}")
    public ResponseEntity<ApiResponse> updateCinema(
            @PathVariable @NonNull Long cinemaId, @RequestBody CinemaRequest request) {
        return ResponseEntity.ok(cinemaService.updateCinema(cinemaId, request));
    }

    @PatchMapping("/cinema/{cinemaId}")
    public ResponseEntity<ApiResponse> toggleCinemaStatus(@PathVariable @NonNull Long cinemaId) {
        return ResponseEntity.ok(cinemaService.toggleStatus(cinemaId));
    }

    @DeleteMapping("/cinema")
    public ResponseEntity<ApiResponse> deactivateCinemas(@RequestBody @NonNull List<Long> ids) {
        return ResponseEntity.ok(cinemaService.deactivateCinemas(ids));
    }

    /*
     * ==========
     * Screen
     * ==========
     */
    @PostMapping("/cinema/{cinemaId}/screen")
    public ResponseEntity<ApiResponse> createScreen(
            @PathVariable @NonNull Long cinemaId, @RequestBody ScreenRequest request) {
        return ResponseEntity.ok(screenService.createScreen(cinemaId, request));
    }

    @GetMapping("/cinema/{cinemaId}/screen")
    public ResponseEntity<List<ScreenProjection>> findAllByCinemaId(
            @PathVariable @NonNull Long cinemaId,
            @RequestParam(required = false, defaultValue = "") String query) {
        return ResponseEntity.ok(screenService.findAllProjectionByQuery(cinemaId, query));
    }

    @GetMapping("/cinema/screen/{screenId}")
    public ResponseEntity<Screen> findScreenById(@PathVariable @NonNull Long screenId) {
        return ResponseEntity.ok(screenService.findById(screenId));
    }

    @PutMapping("/cinema/screen/{screenId}")
    public ResponseEntity<ApiResponse> updateScreen(
            @PathVariable @NonNull Long screenId, @RequestBody ScreenRequest request) {
        return ResponseEntity.ok(screenService.updateScreen(screenId, request));
    }

    @PatchMapping("/cinema/screen/{screenId}")
    public ResponseEntity<ApiResponse> toggleScreenStatus(@PathVariable @NonNull Long screenId) {
        return ResponseEntity.ok(screenService.toggleStatus(screenId));
    }

    @DeleteMapping("/cinema/screen")
    public ResponseEntity<ApiResponse> deactivateScreens(@RequestBody @NonNull List<Long> ids) {
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
    public ResponseEntity<ApiResponse> initSeatMap(
            @PathVariable Long screenId,
            @Valid @RequestBody List<SeatHelperInput> seatHelperInputs) {
        return ResponseEntity.ok(seatService.initSeatMap(screenId, seatHelperInputs));
    }

    @PutMapping("/screen/{screenId}/seat")
    public ResponseEntity<ApiResponse> updateSeatMap(
            @PathVariable Long screenId,
            @Valid @RequestBody List<SeatHelperInput> seatHelperInputs) {
        return ResponseEntity.ok(seatService.updateSeatMap(screenId, seatHelperInputs));
    }

    /*
     * ========
     * schedule
     * ========
     */
    @PostMapping("/cinema/schedule")
    public ResponseEntity<ApiResponse> addSchedules(
            @RequestBody List<MovieScheduleRequest> requests) {
        return ResponseEntity.ok(movieService.addSchedules(requests));
    }

    @Operation(summary = "Find all schedule by cinema")
    @GetMapping("/cinema/{cinemaId}/schedule")
    public ResponseEntity<List<ScheduleOfScreenResponse>> findAllScheduleByCinema(
            @PathVariable @NonNull Long cinemaId) {
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
    public ResponseEntity<List<FilterScreen>> filterByScreen(@PathVariable @NonNull Long cinemaId) {
        return ResponseEntity.ok(cinemaService.filterByScreen(cinemaId));
    }
}
