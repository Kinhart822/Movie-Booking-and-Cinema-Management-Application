package vn.edu.usth.mcma.backend.controller.admin;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import vn.edu.usth.mcma.backend.dto.CinemaRequest;
import vn.edu.usth.mcma.backend.exception.ApiResponse;
import vn.edu.usth.mcma.backend.service.CinemaService;
import vn.edu.usth.mcma.backend.entity.Cinema;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class CinemaController {
    private final CinemaService cinemaService;
    @PostMapping("/cinema")
    public ApiResponse createCinema(@RequestBody CinemaRequest request, HttpServletRequest hsRequest) {
        return cinemaService.createCinema(request, hsRequest);
    }
    @GetMapping("/cinema")
    public List<Cinema> findAll(@RequestParam String query, @PageableDefault Pageable pageable) {
        return cinemaService.findAll(query, pageable);
    }
    @GetMapping("/cinema/{id}")
    public Cinema findById(@PathVariable Long id) {
        return cinemaService.findById(id);
    }
    @PutMapping("/cinema/{id}")
    public ApiResponse updateCinema(@PathVariable Long id, @RequestBody CinemaRequest request, HttpServletRequest hsRequest) {
        return cinemaService.updateCinema(id, request, hsRequest);
    }
    @DeleteMapping("/cinema/{id}")
    public ApiResponse deleteCinema(@PathVariable Long id, HttpServletRequest hsRequest) {
        return cinemaService.deleteCinema(id, hsRequest);
    }
}
