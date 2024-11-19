package vn.edu.usth.mcma.service.admin.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import vn.edu.usth.mcma.service.admin.dto.CinemaRequest;
import vn.edu.usth.mcma.service.admin.service.CinemaService;
import vn.edu.usth.mcma.service.common.CommonResponse;
import vn.edu.usth.mcma.service.common.dao.Cinema;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class CinemaController {
    private final CinemaService cinemaService;
    @PostMapping("/cinema")
    public CommonResponse createCinema(@RequestBody CinemaRequest request) {
        return cinemaService.createCinema(request);
    }
    @GetMapping("/cinema")
    public List<Cinema> findAll() {
        return cinemaService.findAll();
    }
    @GetMapping("/cinema/{id}")
    public Cinema findById(@PathVariable Long id) {
        return cinemaService.findById(id);
    }
    @PutMapping("/cinema/{id}")
    public CommonResponse updateCinema(@PathVariable Long id, @RequestBody CinemaRequest request) {
        return cinemaService.updateCinema(id, request);
    }
    @DeleteMapping("/cinema/{id}")
    public CommonResponse deleteCinema(@PathVariable Long id) {
        return cinemaService.deleteCinema(id);
    }
}
