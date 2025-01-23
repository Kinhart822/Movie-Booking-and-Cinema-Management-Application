package vn.edu.usth.mcma.backend.controller.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.edu.usth.mcma.backend.dto.AdvertisementRepresentation;
import vn.edu.usth.mcma.backend.dto.HighRatingMovie;
import vn.edu.usth.mcma.backend.dto.MovieDetail;
import vn.edu.usth.mcma.backend.dto.MovieDetailShort;
import vn.edu.usth.mcma.backend.service.MovieService;
import vn.edu.usth.mcma.backend.service.ViewService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user/view")
public class ViewController {
    private final MovieService movieService;
    private final ViewService viewService;

    @Deprecated
    @GetMapping("/movie/high-rating")
    public ResponseEntity<List<HighRatingMovie>> findAllHighRating() {
        return ResponseEntity.ok(viewService.findAllHighRating());
    }
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

    @GetMapping("/movie/{id}")
    public ResponseEntity<MovieDetail> findMovieDetail(@PathVariable Long id) {
        return ResponseEntity.ok(viewService.findMovieDetail(id));
    }
}
