package vn.edu.usth.mcma.backend.service;

import constants.ApiResponseCode;
import constants.CommonStatus;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.edu.usth.mcma.backend.dto.ScreenRequest;
import vn.edu.usth.mcma.backend.entity.Screen;
import vn.edu.usth.mcma.backend.exception.ApiResponse;
import vn.edu.usth.mcma.backend.exception.BusinessException;
import vn.edu.usth.mcma.backend.repository.ScreenRepository;
import vn.edu.usth.mcma.backend.security.JwtUtil;

import java.time.Instant;
import java.util.List;

@Transactional
@Service
@AllArgsConstructor
public class ScreenService {
    private final ScreenRepository screenRepository;
    private final JwtUtil jwtUtil;
    public ApiResponse createScreen(ScreenRequest request) {
        Long userId = jwtUtil.getUserIdFromToken();
        Screen screen = new Screen();
//      debug  screen.setCinemaId(request.getCinemaId());
        screen.setName(request.getName());
//      debug  screen.setTypeId(request.getTypeId());
        screen.setStatus(CommonStatus.ACTIVE.getStatus());
        screen.setCreatedBy(userId);
        screen.setLastModifiedBy(userId);
        screenRepository.save(screen);
        return ApiResponse.success();
    }
    public List<Screen> findAll(String query, Pageable pageable) {
        return screenRepository.findAllByNameContaining(query, pageable);
    }
    public ApiResponse updateScreen(Long id, ScreenRequest request) {
        Long userId = jwtUtil.getUserIdFromToken();
        Screen screen = screenRepository
                .findById(id)
                .orElseThrow(() -> new BusinessException(ApiResponseCode.ENTITY_NOT_FOUND));
        // changing cinemaId is not allowed think about it :)
        screen.setName(request.getName());
//      debug  screen.setTypeId(request.getTypeId());
        screen.setLastModifiedBy(userId);
        screen.setLastModifiedDate(Instant.now());
        screenRepository.save(screen);
        return ApiResponse.success();
    }
    public ApiResponse deleteScreen(Long id) {
        Long userId = jwtUtil.getUserIdFromToken();
        Screen screen = screenRepository
                .findById(id)
                .orElseThrow(() -> new BusinessException(ApiResponseCode.ENTITY_NOT_FOUND));
        screen.setStatus(CommonStatus.INACTIVE.getStatus());
        screen.setLastModifiedBy(userId);
        screen.setLastModifiedDate(Instant.now());
        screenRepository.save(screen);
        return ApiResponse.success();
    }
}
