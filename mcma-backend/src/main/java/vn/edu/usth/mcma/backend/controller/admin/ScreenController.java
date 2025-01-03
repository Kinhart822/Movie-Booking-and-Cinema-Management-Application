package vn.edu.usth.mcma.backend.controller.admin;

import constants.ApiResponseCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import vn.edu.usth.mcma.backend.dto.ScreenRequest;
import vn.edu.usth.mcma.backend.entity.Screen;
import vn.edu.usth.mcma.backend.exception.ApiResponse;
import vn.edu.usth.mcma.backend.exception.BusinessException;
import vn.edu.usth.mcma.backend.repository.ScreenRepository;
import vn.edu.usth.mcma.backend.service.ScreenService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class ScreenController {
    private final ScreenService screenService;
    private final ScreenRepository screenRepository;
    @PostMapping("/screen")
    public ApiResponse createScreen(@RequestBody ScreenRequest request) {
        return screenService.createScreen(request);
    }
    @GetMapping("/screen")
    public List<Screen> findAll(@RequestParam(required = false, defaultValue = "") String query, @PageableDefault Pageable pageable) {
        return screenService.findAll(query, pageable);
    }
    @GetMapping("/screen/{id}")
    public Screen findById(@PathVariable Long id) {
        return screenRepository
                .findById(id)
                .orElseThrow(() -> new BusinessException(ApiResponseCode.ENTITY_NOT_FOUND));
    }
    @PutMapping("/screen/{id}")
    public ApiResponse updateScreen(@PathVariable Long id, @RequestBody ScreenRequest request) {
        return screenService.updateScreen(id, request);
    }
    @DeleteMapping("/screen/{id}")
    public ApiResponse deleteScreen(@PathVariable Long id) {
        return screenService.deleteScreen(id);
    }
}
