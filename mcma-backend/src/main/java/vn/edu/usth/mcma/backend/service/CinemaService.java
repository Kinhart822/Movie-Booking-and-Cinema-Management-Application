package vn.edu.usth.mcma.backend.service;

import constants.ApiResponseCode;
import constants.CommonStatus;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import vn.edu.usth.mcma.backend.dto.CinemaProjection;
import vn.edu.usth.mcma.backend.exception.ApiResponse;
import vn.edu.usth.mcma.backend.exception.BusinessException;
import vn.edu.usth.mcma.backend.repository.CityRepository;
import vn.edu.usth.mcma.backend.security.JwtUtil;
import vn.edu.usth.mcma.backend.dto.CinemaRequest;
import vn.edu.usth.mcma.backend.repository.CinemaRepository;
import vn.edu.usth.mcma.backend.entity.Cinema;

import java.time.Instant;
import java.util.List;

@Transactional
@Service
@AllArgsConstructor
public class CinemaService {
    private final CinemaRepository cinemaRepository;
    private final JwtUtil jwtUtil;
    private final CityRepository cityRepository;
    public ApiResponse createCinema(CinemaRequest request) {
        Long userId = jwtUtil.getUserIdFromToken();
        Cinema cinema = new Cinema();
//      debug  cinema.setCityId(request.getCityId());
        cinema.setName(request.getName());
        cinema.setStatus(CommonStatus.ACTIVE.getStatus());
        cinema.setCreatedBy(userId);
        cinema.setLastModifiedBy(userId);
        cinemaRepository.save(cinema);
        return ApiResponse.success();
    }
    public List<CinemaProjection> findAll(String query) {
        return cinemaRepository.findAllProjectionByQuery(query);
    }
    public ApiResponse updateCinema(Long id, CinemaRequest request) {
        Long userId = jwtUtil.getUserIdFromToken();
        Cinema cinema = cinemaRepository
                .findById(id)
                .orElseThrow(() -> new BusinessException(ApiResponseCode.ENTITY_NOT_FOUND));
        // changing cityId is not allowed think about it :)
        cinema.setName(request.getName());
        cinema.setLastModifiedBy(userId);
        cinema.setLastModifiedDate(Instant.now());
        cinemaRepository.save(cinema);
        return ApiResponse.success();
    }
    public ApiResponse deleteCinema(Long id) {
        Long userId = jwtUtil.getUserIdFromToken();
        Cinema cinema = cinemaRepository
                .findById(id)
                .orElseThrow(() -> new BusinessException(ApiResponseCode.ENTITY_NOT_FOUND));
        cinema.setStatus(CommonStatus.DELETED.getStatus());
        cinema.setLastModifiedBy(userId);
        cinema.setLastModifiedDate(Instant.now());
        cinemaRepository.save(cinema);
        return ApiResponse.success();
    }
}
