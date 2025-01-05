package vn.edu.usth.mcma.backend.service;

import constants.ApiResponseCode;
import constants.CommonStatus;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import vn.edu.usth.mcma.backend.dto.ScreenProjection;
import vn.edu.usth.mcma.backend.dto.ScreenRequest;
import vn.edu.usth.mcma.backend.entity.Cinema;
import vn.edu.usth.mcma.backend.entity.Screen;
import vn.edu.usth.mcma.backend.entity.ScreenType;
import vn.edu.usth.mcma.backend.exception.ApiResponse;
import vn.edu.usth.mcma.backend.exception.BusinessException;
import vn.edu.usth.mcma.backend.repository.CinemaRepository;
import vn.edu.usth.mcma.backend.repository.ScreenRepository;
import vn.edu.usth.mcma.backend.repository.ScreenTypeRepository;
import vn.edu.usth.mcma.backend.security.JwtUtil;

import java.time.Instant;
import java.util.List;

@Transactional
@Service
@AllArgsConstructor
public class ScreenService {
    private final ScreenRepository screenRepository;
    private final JwtUtil jwtUtil;
    private final CinemaRepository cinemaRepository;
    private final ScreenTypeRepository screenTypeRepository;

    public ApiResponse createScreen(Long cinemaId, ScreenRequest request) {
        Cinema cinema = cinemaRepository
                .findById(cinemaId)
                .orElseThrow(() -> new BusinessException(ApiResponseCode.ENTITY_NOT_FOUND));
        ScreenType screenType = screenTypeRepository
                .findById(request.getTypeId())
                .orElseThrow(() -> new BusinessException(ApiResponseCode.ENTITY_NOT_FOUND));
        Long userId = jwtUtil.getUserIdFromToken();
        Instant now = Instant.now();
        screenRepository
                .save(Screen
                        .builder()
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
        return ApiResponse.success();
    }
    public List<ScreenProjection> findAllProjectionByQuery(Long cinemaId, String query) {
        Cinema cinema = cinemaRepository
                .findById(cinemaId)
                .orElseThrow(() -> new BusinessException(ApiResponseCode.ENTITY_NOT_FOUND));
        return screenRepository.findAllProjectionByQuery(cinema.getId(), query);
    }
    public Screen findById(Long screenId) {
        return screenRepository.findById(screenId).orElseThrow(() -> new BusinessException(ApiResponseCode.ENTITY_NOT_FOUND));
    }
    public ApiResponse updateScreen(Long id, ScreenRequest request) {
        Screen screen = screenRepository
                .findById(id)
                .orElseThrow(() -> new BusinessException(ApiResponseCode.ENTITY_NOT_FOUND));
        ScreenType screenType = screenTypeRepository
                .findById(request.getTypeId())
                .orElseThrow(() -> new BusinessException(ApiResponseCode.ENTITY_NOT_FOUND));
        Long userId = jwtUtil.getUserIdFromToken();
        Instant now = Instant.now();
        screenRepository.save(screen
                .toBuilder()
                .name(request.getName())
                .screenType(screenType)
                .status(request.getStatus())
                .lastModifiedBy(userId)
                .lastModifiedDate(now)
                .build());
        return ApiResponse.success();
    }
    public ApiResponse toggleStatus(Long screenId) {
        Screen screen = screenRepository
                .findById(screenId)
                .orElseThrow(() -> new BusinessException(ApiResponseCode.ENTITY_NOT_FOUND));
        screenRepository.save(screen
                .toBuilder()
                .status(CommonStatus.ACTIVE.getStatus() + CommonStatus.INACTIVE.getStatus() - screen.getStatus())
                .lastModifiedBy(jwtUtil.getUserIdFromToken())
                .lastModifiedDate(Instant.now())
                .build());
        return ApiResponse.success();
    }
    public ApiResponse deactivateScreens(List<Long> ids) {
        Long userId = jwtUtil.getUserIdFromToken();
        Instant now = Instant.now();
        screenRepository
                .saveAll(screenRepository
                        .findAllById(ids)
                        .stream()
                        .map(s -> s
                                .toBuilder()
                                .status(CommonStatus.INACTIVE.getStatus())
                                .lastModifiedBy(userId)
                                .lastModifiedDate(now)
                                .build())
                        .toList());
        return ApiResponse.success();
    }
}
