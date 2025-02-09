package vn.edu.usth.mcma.backend.controller.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.usth.mcma.backend.dto.*;
import vn.edu.usth.mcma.backend.dto.movie.*;
import vn.edu.usth.mcma.backend.service.ViewService;

import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user/view")
public class ViewController {
    private final ViewService viewService;

    @GetMapping("/movie/advertisement")
    public ResponseEntity<List<AdvertisementRepresentation>> findAllAdvertisement() {
        return ResponseEntity.ok(viewService.findAllAdvertisement());
    }
    @GetMapping("/movie/now-showing")
    public ResponseEntity<List<MovieDetailShort>> findAllNowShowing() {
        return ResponseEntity.ok(viewService.findAllNowShowing());
    }
    @GetMapping("/movie/coming-soon")
    public ResponseEntity<List<MovieDetailShort>> findAllComingSoon() {
        return ResponseEntity.ok(viewService.findAllComingSoon());
    }
    @GetMapping("/movie/{id}/detail")
    public ResponseEntity<MovieDetail> findMovieDetail(@PathVariable Long id) {
        return ResponseEntity.ok(viewService.findMovieDetail(id));
    }

    @GetMapping("/genre")
    public ResponseEntity<List<GenreShort>> findAllGenre() {
        return ResponseEntity.ok(viewService.findAllGenre());
    }

    @PostMapping("/movie/genre")
    public ResponseEntity<List<MovieDetailShort>> getAllMovies(
            @RequestParam(required = false, name = "name") String name,
            @RequestBody Set<Long> ids) {
        return ResponseEntity.ok(viewService.findAllMovieByGenre(name, ids));
    }
    @GetMapping("/movie/{id}/short-detail")
    public ResponseEntity<MovieDetailShort2> findMovieDetailShort(@PathVariable Long id) {
        return ResponseEntity.ok(viewService.findMovieDetailShort(id));
    }
    @GetMapping("/movie/{id}/showtime")
    public ResponseEntity<List<ShowtimeOfMovieByCity>> findAllShowtimeByMovie(@PathVariable Long id) {
        return ResponseEntity.ok(viewService.findAllShowtimeByMovie(id));
    }
}
