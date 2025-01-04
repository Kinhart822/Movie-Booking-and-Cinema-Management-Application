package vn.edu.usth.mcma.backend.service;

import constants.ApiResponseCode;
import constants.CommonStatus;
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

@Service
@AllArgsConstructor
public class CinemaService {
    private final CinemaRepository cinemaRepository;
    private final CityRepository cityRepository;
    private final JwtUtil jwtUtil;

    public ApiResponse createCinema(CinemaRequest request) {
        Long userId = jwtUtil.getUserIdFromToken();
        Instant now = Instant.now();
        cinemaRepository
                .save(Cinema
                        .builder()
                        .city(cityRepository
                                .findById(request.getCityId())
                                .orElseThrow(() -> new BusinessException(ApiResponseCode.ENTITY_NOT_FOUND)))
                        .name(request.getName())
                        .address(request.getAddress())
                        .status(CommonStatus.ACTIVE.getStatus())
                        .createdBy(userId)
                        .createdDate(now)
                        .lastModifiedBy(userId)
                        .lastModifiedDate(now)
                        .build());
        return ApiResponse.success();
    }
    public List<CinemaProjection> findAll(String query) {
        return cinemaRepository.findAllProjectionByQuery(query);
    }
    public Cinema findById(Long id) {
        return cinemaRepository.findById(id).orElseThrow(() -> new BusinessException(ApiResponseCode.ENTITY_NOT_FOUND));
    }
    public ApiResponse updateCinema(Long id, CinemaRequest request) {
        Cinema cinema = cinemaRepository.findById(id).orElseThrow(() -> new BusinessException(ApiResponseCode.ENTITY_NOT_FOUND));
        cinemaRepository
                .save(Cinema
                        .builder()
                        .id(cinema.getId())
                        .city(cinema.getCity())
                        .name(request.getName())
                        .address(request.getAddress())
                        .status(request.getStatus())
                        .lastModifiedBy(jwtUtil.getUserIdFromToken())
                        .lastModifiedDate(Instant.now())
                        .build());
        return ApiResponse.success();
    }
    public ApiResponse toggleStatus(Long id) {
        Cinema cinema = cinemaRepository.findById(id).orElseThrow(() -> new BusinessException(ApiResponseCode.ENTITY_NOT_FOUND));
        cinemaRepository
                .save(Cinema
                        .builder()
                        .id(cinema.getId())
                        .city(cinema.getCity())
                        .name(cinema.getName())
                        .address(cinema.getAddress())
                        .status(CommonStatus.ACTIVE.getStatus() + CommonStatus.INACTIVE.getStatus() - cinema.getStatus())
                        .lastModifiedBy(jwtUtil.getUserIdFromToken())
                        .lastModifiedDate(Instant.now())
                        .build());
        return ApiResponse.success();
    }
    public ApiResponse deactivateCinemas(List<Long> ids) {
        cinemaRepository
                .saveAll(cinemaRepository
                        .findAllById(ids)
                        .stream()
                        .map(c -> Cinema
                                .builder()
                                .id(c.getId())
                                .city(c.getCity())
                                .name(c.getName())
                                .address(c.getAddress())
                                .status(CommonStatus.INACTIVE.getStatus()).build())
                        .toList());
        return ApiResponse.success();
    }
}
