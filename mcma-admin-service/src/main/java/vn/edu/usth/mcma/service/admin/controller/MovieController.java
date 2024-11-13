package vn.edu.usth.mcma.service.admin.controller;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.usth.mcma.service.admin.service.MovieService;
import vn.edu.usth.mcma.service.admin.service.dto.request.MovieRequestDTO;
import vn.edu.usth.mcma.service.admin.service.dto.response.MovieProjection;
import vn.edu.usth.mcma.service.common.dto.response.CommonResponseDTO;
import vn.edu.usth.mcma.service.common.domain.Movie;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class MovieController {
    // TODO log
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final MovieService movieService;

    @GetMapping("/movie")
    public ResponseEntity<List<MovieProjection>> getAllMovies(
            @RequestParam(required = false, name = "title") String title,
            @RequestParam(required = false, name = "limit", defaultValue = "10") Integer limit,
            @RequestParam(required = false, name = "offset", defaultValue = "0") Integer offset) {
        log.debug("REST request to get all movies");
        return ResponseEntity.ok(movieService.getAllMovies(title, limit, offset));
    }

    @PostMapping("/movie")
    public ResponseEntity<CommonResponseDTO> addMovie(@RequestBody MovieRequestDTO movieRequestDTO) {
        log.debug("REST request to add movie : {}", movieRequestDTO);
        movieService.addMovie(movieRequestDTO);
        return ResponseEntity.ok(CommonResponseDTO.successResponse());
    }
}
