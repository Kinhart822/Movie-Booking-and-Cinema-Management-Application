package vn.edu.usth.mcma.backend.controller.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.edu.usth.mcma.backend.dto.MovieDetail;
import vn.edu.usth.mcma.backend.dto.MovieDetailShort;
import vn.edu.usth.mcma.backend.service.MovieService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class MovieController {
    private final MovieService movieService;

    @GetMapping("/movie/now-showing")
    public ResponseEntity<List<MovieDetailShort>> findAllNowShowing() {
        return ResponseEntity.ok(movieService.findAllNowShowing());
    }

    @GetMapping("/movie/{id}")
    public ResponseEntity<MovieDetail> findMovieDetail(@PathVariable Long id) {
        return ResponseEntity.ok(movieService.findMovieDetail(id));
    }
}
