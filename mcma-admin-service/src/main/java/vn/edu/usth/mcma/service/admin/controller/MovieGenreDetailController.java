package vn.edu.usth.mcma.service.admin.controller;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.usth.mcma.service.admin.service.MovieGenreDetailService;
import vn.edu.usth.mcma.service.admin.service.dto.request.MovieGenreDetailRequestDTO;
import vn.edu.usth.mcma.service.common.constants.ApiResponseCode;
import vn.edu.usth.mcma.service.common.domain.MovieGenreDetail;
import vn.edu.usth.mcma.service.common.dto.response.CommonResponseDTO;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class MovieGenreDetailController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final MovieGenreDetailService movieGenreDetailService;

    @GetMapping("/movie-genre-detail")
    public ResponseEntity<List<MovieGenreDetail>> getAllMovieGenreDetails() {
        log.debug("REST request to get all MovieGenreDetails");
        return ResponseEntity.ok(movieGenreDetailService.getAllMovieGenreDetails());
    }

    @PostMapping("/movie-genre-detail")
    public ResponseEntity<CommonResponseDTO> addMovieGenreDetail(@RequestBody MovieGenreDetailRequestDTO movieGenreDetailRequestDTO) {
        log.debug("REST request to add MovieGenreDetail : {}", movieGenreDetailRequestDTO);
        movieGenreDetailService.addMovieGenreDetail(movieGenreDetailRequestDTO);
        return ResponseEntity.ok(CommonResponseDTO.successResponse());
    }
}
