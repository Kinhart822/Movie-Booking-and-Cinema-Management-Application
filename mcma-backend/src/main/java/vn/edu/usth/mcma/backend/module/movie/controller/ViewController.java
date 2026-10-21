package vn.edu.usth.mcma.backend.module.movie.controller;

import java.util.List;
import java.util.Set;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import vn.edu.usth.mcma.backend.module.movie.dto.response.AdvertisementRepresentation;
import vn.edu.usth.mcma.backend.module.movie.dto.response.MovieDetail;
import vn.edu.usth.mcma.backend.module.movie.dto.response.MovieDetailShort;
import vn.edu.usth.mcma.backend.module.movie.dto.response.MovieDetailShort2;
import vn.edu.usth.mcma.backend.module.movie.dto.response.ShowtimeOfMovieByCity;
import vn.edu.usth.mcma.backend.module.movie.projection.GenreShort;
import vn.edu.usth.mcma.backend.module.movie.service.ViewService;

import lombok.RequiredArgsConstructor;

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
    public ResponseEntity<List<ShowtimeOfMovieByCity>> findAllShowtimeByMovie(
            @PathVariable Long id) {
        return ResponseEntity.ok(viewService.findAllShowtimeByMovie(id));
    }
}
