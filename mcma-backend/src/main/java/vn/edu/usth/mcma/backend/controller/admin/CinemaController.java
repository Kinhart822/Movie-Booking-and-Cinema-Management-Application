package vn.edu.usth.mcma.backend.controller.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.usth.mcma.backend.dto.CinemaProjection;
import vn.edu.usth.mcma.backend.dto.CinemaRequest;
import vn.edu.usth.mcma.backend.exception.ApiResponse;
import vn.edu.usth.mcma.backend.repository.CinemaRepository;
import vn.edu.usth.mcma.backend.service.CinemaService;
import vn.edu.usth.mcma.backend.entity.Cinema;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class CinemaController {
    private final CinemaService cinemaService;
    private final CinemaRepository cinemaRepository;

    @PostMapping("/cinema")
    public ResponseEntity<ApiResponse> createCinema(@RequestBody CinemaRequest request) {
        return ResponseEntity.ok(cinemaService.createCinema(request));
    }
    @GetMapping("/cinema")
    public ResponseEntity<List<CinemaProjection>> findAll(@RequestParam(required = false, defaultValue = "") String query) {
        return ResponseEntity.ok(cinemaService.findAll(query));
    }
    @GetMapping("/cinema/{id}")
    public ResponseEntity<Cinema> findById(@PathVariable Long id) {
        return ResponseEntity.ok(cinemaService.findById(id));
    }
    @PutMapping("/cinema/{id}")
    public ResponseEntity<ApiResponse> updateCinema(@PathVariable Long id, @RequestBody CinemaRequest request) {
        return ResponseEntity.ok(cinemaService.updateCinema(id, request));
    }
    @PatchMapping("/cinema/{id}")
    public ResponseEntity<ApiResponse> toggleStatus(@PathVariable Long id) {
        return ResponseEntity.ok(cinemaService.toggleStatus(id));
    }
    @DeleteMapping("/cinema")
    public ResponseEntity<ApiResponse> deactivateCinemas(@RequestBody List<Long> ids) {
        return ResponseEntity.ok(cinemaService.deactivateCinemas(ids));
    }
}
