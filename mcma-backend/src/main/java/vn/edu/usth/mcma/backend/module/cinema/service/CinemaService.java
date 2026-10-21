package vn.edu.usth.mcma.backend.module.cinema.service;

import java.time.Instant;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.edu.usth.mcma.backend.common.constant.ApiResponseCode;
import vn.edu.usth.mcma.backend.common.dto.ApiResponse;
import vn.edu.usth.mcma.backend.common.enums.CommonStatus;
import vn.edu.usth.mcma.backend.common.exception.BusinessException;
import vn.edu.usth.mcma.backend.module.cinema.dto.request.CinemaRequest;
import vn.edu.usth.mcma.backend.module.cinema.dto.request.FilterCinema;
import vn.edu.usth.mcma.backend.module.cinema.dto.request.FilterScreen;
import vn.edu.usth.mcma.backend.module.cinema.dto.response.CinemaDetailShort;
import vn.edu.usth.mcma.backend.module.cinema.entity.Cinema;
import vn.edu.usth.mcma.backend.module.cinema.projection.CinemaProjection;
import vn.edu.usth.mcma.backend.module.cinema.repository.CinemaRepository;
import vn.edu.usth.mcma.backend.module.cinema.repository.CityRepository;
import vn.edu.usth.mcma.backend.module.cinema.repository.ScreenRepository;
import vn.edu.usth.mcma.backend.security.jwt.JwtHelper;

import lombok.AllArgsConstructor;

@Transactional
@Service
@AllArgsConstructor
public class CinemaService {
    private final CinemaRepository cinemaRepository;
    private final CityRepository cityRepository;
    private final JwtHelper jwtHelper;
    private final ScreenRepository screenRepository;

    /*
     * ADMIN
     */
    public ApiResponse createCinema(CinemaRequest request) {
        Long userId = jwtHelper.getIdUserRequesting();
        Instant now = Instant.now();
        Cinema cinemaToCreate =
                Cinema.builder()
                        .city(
                                cityRepository
                                        .findById(request.getCityId())
                                        .orElseThrow(
                                                () ->
                                                        new BusinessException(
                                                                ApiResponseCode.ENTITY_NOT_FOUND)))
                        .name(request.getName())
                        .address(request.getAddress())
                        .status(CommonStatus.ACTIVE.getStatus())
                        .createdBy(userId)
                        .createdDate(now)
                        .lastModifiedBy(userId)
                        .lastModifiedDate(now)
                        .build();
        cinemaRepository.save(cinemaToCreate);
        return ApiResponse.ok();
    }

    public List<CinemaProjection> findAll(String query) {
        return cinemaRepository.findAllProjectionByQuery(query);
    }

    public Cinema findById(Long id) {
        return cinemaRepository
                .findById(id)
                .orElseThrow(() -> new BusinessException(ApiResponseCode.ENTITY_NOT_FOUND));
    }

    public ApiResponse updateCinema(Long id, CinemaRequest request) {
        Cinema cinema =
                cinemaRepository
                        .findById(id)
                        .orElseThrow(() -> new BusinessException(ApiResponseCode.ENTITY_NOT_FOUND));
        Cinema updatedCinema =
                cinema.toBuilder()
                        .name(request.getName())
                        .address(request.getAddress())
                        .status(request.getStatus())
                        .lastModifiedBy(jwtHelper.getIdUserRequesting())
                        .lastModifiedDate(Instant.now())
                        .build();
        cinemaRepository.save(updatedCinema);
        return ApiResponse.ok();
    }

    public ApiResponse toggleStatus(Long id) {
        Cinema cinema =
                cinemaRepository
                        .findById(id)
                        .orElseThrow(() -> new BusinessException(ApiResponseCode.ENTITY_NOT_FOUND));
        Cinema updatedCinema =
                cinema.toBuilder()
                        .status(
                                CommonStatus.ACTIVE.getStatus()
                                        + CommonStatus.INACTIVE.getStatus()
                                        - cinema.getStatus())
                        .lastModifiedBy(jwtHelper.getIdUserRequesting())
                        .lastModifiedDate(Instant.now())
                        .build();
        cinemaRepository.save(updatedCinema);
        return ApiResponse.ok();
    }

    public ApiResponse deactivateCinemas(List<Long> ids) {
        Long userId = jwtHelper.getIdUserRequesting();
        Instant now = Instant.now();
        List<Cinema> cinemasToDeactivate =
                cinemaRepository.findAllById(ids).stream()
                        .<Cinema>map(
                                c ->
                                        c.toBuilder()
                                                .status(CommonStatus.INACTIVE.getStatus())
                                                .lastModifiedBy(userId)
                                                .lastModifiedDate(now)
                                                .build())
                        .toList();
        cinemaRepository.saveAll(cinemasToDeactivate);
        return ApiResponse.ok();
    }

    /*
     * USER
     */
    public List<CinemaDetailShort> findAllCinemaByCity(Long cityId) {
        return cinemaRepository
                .findAllByCity(
                        cityRepository
                                .findById(cityId)
                                .orElseThrow(
                                        () ->
                                                new BusinessException(
                                                        ApiResponseCode.ENTITY_NOT_FOUND)))
                .stream()
                .<CinemaDetailShort>map(
                        c ->
                                CinemaDetailShort.builder()
                                        .id(c.getId())
                                        .name(c.getName())
                                        .address(c.getAddress())
                                        .build())
                .toList();
    }

    public List<FilterCinema> filterByCinema() {
        return cinemaRepository.findAll().stream()
                .<FilterCinema>map(
                        c ->
                                FilterCinema.builder()
                                        .cinemaId(c.getId())
                                        .cinemaName(c.getName())
                                        .build())
                .toList();
    }

    public List<FilterScreen> filterByScreen(Long cinemaId) {
        return screenRepository
                .findAllByCinema(
                        cinemaRepository
                                .findById(cinemaId)
                                .orElseThrow(
                                        () ->
                                                new BusinessException(
                                                        ApiResponseCode.ENTITY_NOT_FOUND)))
                .stream()
                .<FilterScreen>map(
                        s ->
                                FilterScreen.builder()
                                        .screenId(s.getId())
                                        .screenName(s.getName())
                                        .build())
                .toList();
    }
}
