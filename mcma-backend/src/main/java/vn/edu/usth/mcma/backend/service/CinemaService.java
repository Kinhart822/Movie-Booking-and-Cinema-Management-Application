package vn.edu.usth.mcma.backend.service;

import constants.CommonStatus;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.edu.usth.mcma.backend.exception.ApiResponse;
import vn.edu.usth.mcma.backend.security.JwtUtil;
import vn.edu.usth.mcma.backend.dto.CinemaRequest;
import vn.edu.usth.mcma.backend.repository.CinemaRepository;
import vn.edu.usth.mcma.backend.entity.Cinema;

import java.time.Instant;
import java.util.List;

@Transactional
@Service
public class CinemaService extends AbstractService<Cinema, Long> {
    private final CinemaRepository cinemaRepository;
    private final JwtUtil jwtUtil;

    public CinemaService(CinemaRepository cinemaRepository, JwtUtil jwtUtil) {
        super(cinemaRepository);
        this.cinemaRepository = cinemaRepository;
        this.jwtUtil = jwtUtil;
    }
    public ApiResponse createCinema(CinemaRequest request) {
        Long userId = jwtUtil.getUserIdFromToken();
        Cinema cinema = new Cinema();
        cinema.setCityId(request.getCityId());
        cinema.setName(request.getName());
        cinema.setStatus(CommonStatus.ACTIVE.getStatus());
        cinema.setCreatedBy(userId);
        cinema.setLastModifiedBy(userId);
        cinemaRepository.save(cinema);
        return this.successResponse();
    }
    public List<Cinema> findAll(String query, Pageable pageable) {
        return cinemaRepository.findAllByNameContaining(query, pageable);
    }
    public ApiResponse updateCinema(Long id, CinemaRequest request) {
        Long userId = jwtUtil.getUserIdFromToken();
        Cinema cinema = findById(id);
        // changing cityId is not allowed think about it :)
        cinema.setName(request.getName());
        cinema.setLastModifiedBy(userId);
        cinema.setLastModifiedDate(Instant.now());
        cinemaRepository.save(cinema);
        return this.successResponse();
    }
    public ApiResponse deleteCinema(Long id) {
        Long userId = jwtUtil.getUserIdFromToken();
        Cinema cinema = findById(id);
        cinema.setStatus(CommonStatus.DELETED.getStatus());
        cinema.setLastModifiedBy(userId);
        cinema.setLastModifiedDate(Instant.now());
        cinemaRepository.save(cinema);
        return this.successResponse();
    }
}
