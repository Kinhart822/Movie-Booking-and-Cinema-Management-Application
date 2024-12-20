package vn.edu.usth.mcma.backend.service;

import constants.CommonStatus;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.edu.usth.mcma.backend.dto.ScreenRequest;
import vn.edu.usth.mcma.backend.entity.Screen;
import vn.edu.usth.mcma.backend.exception.ApiResponse;
import vn.edu.usth.mcma.backend.repository.ScreenRepository;
import vn.edu.usth.mcma.backend.security.JwtUtil;

import java.time.Instant;
import java.util.List;

@Transactional
@Service
public class ScreenService extends AbstractService<Screen, Long> {
    private final ScreenRepository screenRepository;
    private final JwtUtil jwtUtil;
    public ScreenService(ScreenRepository screenRepository, JwtUtil jwtUtil) {
        super(screenRepository);
        this.screenRepository = screenRepository;
        this.jwtUtil = jwtUtil;
    }
    public ApiResponse createScreen(ScreenRequest request, HttpServletRequest hsRequest) {
        Long userId = jwtUtil.getUserIdFromToken(hsRequest);
        Screen screen = new Screen();
        screen.setCinemaId(request.getCinemaId());
        screen.setName(request.getName());
        screen.setTypeId(request.getTypeId());
        screen.setStatus(CommonStatus.ACTIVE.getStatus());
        screen.setCreatedBy(userId);
        screen.setLastModifiedBy(userId);
        screenRepository.save(screen);
        return this.successResponse();
    }
    public List<Screen> findAll(String query, Pageable pageable) {
        return screenRepository.findAllByNameContaining(query, pageable);
    }
    public ApiResponse updateScreen(Long id, ScreenRequest request, HttpServletRequest hsRequest) {
        Long userId = jwtUtil.getUserIdFromToken(hsRequest);
        Screen screen = findById(id);
        // changing cinemaId is not allowed think about it :)
        screen.setName(request.getName());
        screen.setTypeId(request.getTypeId());
        screen.setLastModifiedBy(userId);
        screen.setLastModifiedDate(Instant.now());
        screenRepository.save(screen);
        return this.successResponse();
    }
    public ApiResponse deleteScreen(Long id, HttpServletRequest hsRequest) {
        Long userId = jwtUtil.getUserIdFromToken(hsRequest);
        Screen screen = findById(id);
        screen.setStatus(CommonStatus.DELETED.getStatus());
        screen.setLastModifiedBy(userId);
        screen.setLastModifiedDate(Instant.now());
        screenRepository.save(screen);
        return this.successResponse();
    }
}
