package vn.edu.usth.mcma.backend.controller.admin;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import vn.edu.usth.mcma.backend.dto.ScreenRequest;
import vn.edu.usth.mcma.backend.entity.Screen;
import vn.edu.usth.mcma.backend.exception.ApiResponse;
import vn.edu.usth.mcma.backend.service.ScreenService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class ScreenController {
    private final ScreenService screenService;
    @PostMapping("/screen")
    public ApiResponse createScreen(@RequestBody ScreenRequest request, HttpServletRequest hsRequest) {
        return screenService.createScreen(request, hsRequest);
    }
    @GetMapping("/screen")
    public List<Screen> findAll(@RequestParam String query, @PageableDefault Pageable pageable) {
        return screenService.findAll(query, pageable);
    }
    @GetMapping("/screen/{id}")
    public Screen findById(@PathVariable Long id) {
        return screenService.findById(id);
    }
    @PutMapping("/screen/{id}")
    public ApiResponse updateScreen(@PathVariable Long id, @RequestBody ScreenRequest request, HttpServletRequest hsRequest) {
        return screenService.updateScreen(id, request, hsRequest);
    }
    @DeleteMapping("/screen/{id}")
    public ApiResponse deleteScreen(@PathVariable Long id, HttpServletRequest hsRequest) {
        return screenService.deleteScreen(id, hsRequest);
    }
}
