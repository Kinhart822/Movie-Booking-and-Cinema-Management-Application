package vn.edu.usth.mcma.backend.module.cinema.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vn.edu.usth.mcma.backend.module.cinema.dto.response.CinemaDetailShort;
import vn.edu.usth.mcma.backend.module.cinema.service.CinemaService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/user")
public class CinemaUserController {
    private final CinemaService cinemaService;

    @GetMapping("/view/city/{cityId}/cinema")
    public ResponseEntity<List<CinemaDetailShort>> findAllCinemaByCity(
            @PathVariable @NonNull Long cityId) {
        return ResponseEntity.ok(cinemaService.findAllCinemaByCity(cityId));
    }
}
