package vn.edu.usth.mcma.service.admin.service;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.edu.usth.mcma.service.admin.repository.CustomMovieGenreDetailRepository;
import vn.edu.usth.mcma.service.admin.service.dto.request.MovieGenreDetailRequestDTO;
import vn.edu.usth.mcma.service.common.domain.MovieGenreDetail;

import java.util.List;

@Transactional
@Service
@AllArgsConstructor
public class MovieGenreDetailService {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final CustomMovieGenreDetailRepository movieGenreDetailRepository;

    public List<MovieGenreDetail> getAllMovieGenreDetails() {
        log.debug("Request to get all MovieGenreDetails");
        return movieGenreDetailRepository.findAll();
    }

    public void addMovieGenreDetail(MovieGenreDetailRequestDTO movieGenreDetailRequestDTO) {
        log.debug("Request to add MovieGenreDetail : {}", movieGenreDetailRequestDTO);
        MovieGenreDetail movieGenreDetail = new MovieGenreDetail();
        movieGenreDetail.setName(movieGenreDetailRequestDTO.getName());
        movieGenreDetail.setDescription(movieGenreDetailRequestDTO.getDescription());
        movieGenreDetailRepository.save(movieGenreDetail);
    }
}
