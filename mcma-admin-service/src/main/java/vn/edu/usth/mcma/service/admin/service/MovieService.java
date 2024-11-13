package vn.edu.usth.mcma.service.admin.service;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.edu.usth.mcma.service.admin.repository.CustomMovieRepository;
import vn.edu.usth.mcma.service.admin.service.dto.request.MovieRequestDTO;
import vn.edu.usth.mcma.service.admin.service.dto.response.MovieProjection;
import vn.edu.usth.mcma.service.common.domain.Movie;
import vn.edu.usth.mcma.service.common.dto.response.CommonResponseDTO;

import java.util.List;

@Transactional
@Service
@AllArgsConstructor
public class MovieService {
    // TODO log
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final CustomMovieRepository movieRepository;

    public List<MovieProjection> getAllMovies(String title, Integer limit, Integer offset) {
        log.debug("Request to get all Movie");
        return movieRepository.getAllMovies(title, limit, offset);
    }

    public void addMovie(MovieRequestDTO movieRequestDTO) {
        log.debug("Request to add Movie");
        Movie movie = new Movie();
        movie.setTitle(movieRequestDTO.getTitle());
        movie.setDescription(movieRequestDTO.getDescription());
        movie.setLength(movieRequestDTO.getLength());
        movie.setDatePublish(movieRequestDTO.getDatePublish());
        // TODO
        movieRepository.save(movie);
    }
}
