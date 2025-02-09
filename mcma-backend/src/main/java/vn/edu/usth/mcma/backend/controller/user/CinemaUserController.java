package vn.edu.usth.mcma.backend.controller.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.edu.usth.mcma.backend.dto.cinema.CinemaDetailShort;
import vn.edu.usth.mcma.backend.service.CinemaService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/user")
public class CinemaUserController {
    private final CinemaService cinemaService;

    @GetMapping("/view/city/{cityId}/cinema")
    public ResponseEntity<List<CinemaDetailShort>> findAllCinemaByCity(@PathVariable Long cityId) {
        return ResponseEntity.ok(cinemaService.findAllCinemaByCity(cityId));
    }
}
