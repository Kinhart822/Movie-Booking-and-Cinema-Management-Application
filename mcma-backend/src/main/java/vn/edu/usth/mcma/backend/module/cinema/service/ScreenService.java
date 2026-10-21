package vn.edu.usth.mcma.backend.module.cinema.service;

import java.time.Instant;
import java.util.List;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import vn.edu.usth.mcma.backend.common.constant.ApiResponseCode;
import vn.edu.usth.mcma.backend.common.dto.ApiResponse;
import vn.edu.usth.mcma.backend.common.enums.CommonStatus;
import vn.edu.usth.mcma.backend.common.exception.BusinessException;
import vn.edu.usth.mcma.backend.module.cinema.dto.request.ScreenRequest;
import vn.edu.usth.mcma.backend.module.cinema.entity.Cinema;
import vn.edu.usth.mcma.backend.module.cinema.entity.Screen;
import vn.edu.usth.mcma.backend.module.cinema.entity.ScreenType;
import vn.edu.usth.mcma.backend.module.cinema.projection.ScreenProjection;
import vn.edu.usth.mcma.backend.module.cinema.repository.CinemaRepository;
import vn.edu.usth.mcma.backend.module.cinema.repository.ScreenRepository;
import vn.edu.usth.mcma.backend.module.cinema.repository.ScreenTypeRepository;
import vn.edu.usth.mcma.backend.security.jwt.JwtHelper;

import lombok.AllArgsConstructor;

@Transactional
@Service
@AllArgsConstructor
public class ScreenService {
    private final ScreenRepository screenRepository;
    private final JwtHelper jwtHelper;
    private final CinemaRepository cinemaRepository;
    private final ScreenTypeRepository screenTypeRepository;

    public ApiResponse createScreen(Long cinemaId, ScreenRequest request) {
        Cinema cinema =
                cinemaRepository
                        .findById(cinemaId)
                        .orElseThrow(() -> new BusinessException(ApiResponseCode.ENTITY_NOT_FOUND));
        ScreenType screenType =
                screenTypeRepository
                        .findById(request.getTypeId())
                        .orElseThrow(() -> new BusinessException(ApiResponseCode.ENTITY_NOT_FOUND));
        Long userId = jwtHelper.getIdUserRequesting();
        Instant now = Instant.now();
        screenRepository.save(
                Screen.builder()
                        .cinema(cinema)
                        .name(request.getName())
                        .screenType(screenType)
                        .status(CommonStatus.ACTIVE.getStatus())
                        .mutable(true)
                        .createdBy(userId)
                        .createdDate(now)
                        .lastModifiedBy(userId)
                        .lastModifiedDate(now)
                        .build());
        return ApiResponse.ok();
    }

    public List<ScreenProjection> findAllProjectionByQuery(Long cinemaId, String query) {
        Cinema cinema =
                cinemaRepository
                        .findById(cinemaId)
                        .orElseThrow(() -> new BusinessException(ApiResponseCode.ENTITY_NOT_FOUND));
        return screenRepository.findAllProjectionByQuery(cinema.getId(), query);
    }

    public Screen findById(Long screenId) {
        return screenRepository
                .findById(screenId)
                .orElseThrow(() -> new BusinessException(ApiResponseCode.ENTITY_NOT_FOUND));
    }

    public ApiResponse updateScreen(Long id, ScreenRequest request) {
        Screen screen =
                screenRepository
                        .findById(id)
                        .orElseThrow(() -> new BusinessException(ApiResponseCode.ENTITY_NOT_FOUND));
        ScreenType screenType =
                screenTypeRepository
                        .findById(request.getTypeId())
                        .orElseThrow(() -> new BusinessException(ApiResponseCode.ENTITY_NOT_FOUND));
        Long userId = jwtHelper.getIdUserRequesting();
        Instant now = Instant.now();
        screenRepository.save(
                screen.toBuilder()
                        .name(request.getName())
                        .screenType(screenType)
                        .status(request.getStatus())
                        .lastModifiedBy(userId)
                        .lastModifiedDate(now)
                        .build());
        return ApiResponse.ok();
    }

    public ApiResponse toggleStatus(Long screenId) {
        Screen screen =
                screenRepository
                        .findById(screenId)
                        .orElseThrow(() -> new BusinessException(ApiResponseCode.ENTITY_NOT_FOUND));
        screenRepository.save(
                screen.toBuilder()
                        .status(
                                CommonStatus.ACTIVE.getStatus()
                                        + CommonStatus.INACTIVE.getStatus()
                                        - screen.getStatus())
                        .lastModifiedBy(jwtHelper.getIdUserRequesting())
                        .lastModifiedDate(Instant.now())
                        .build());
        return ApiResponse.ok();
    }

    public ApiResponse deactivateScreens(List<Long> ids) {
        Long userId = jwtHelper.getIdUserRequesting();
        Instant now = Instant.now();
        screenRepository.saveAll(
                screenRepository.findAllById(ids).stream()
                        .map(
                                s ->
                                        s.toBuilder()
                                                .status(CommonStatus.INACTIVE.getStatus())
                                                .lastModifiedBy(userId)
                                                .lastModifiedDate(now)
                                                .build())
                        .toList());
        return ApiResponse.ok();
    }
}
