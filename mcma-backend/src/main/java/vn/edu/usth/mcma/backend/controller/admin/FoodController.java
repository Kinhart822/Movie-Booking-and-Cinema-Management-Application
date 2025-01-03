package vn.edu.usth.mcma.backend.controller.admin;

import constants.ApiResponseCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import vn.edu.usth.mcma.backend.entity.Food;
import vn.edu.usth.mcma.backend.dto.FoodRequest;
import vn.edu.usth.mcma.backend.exception.ApiResponse;
import vn.edu.usth.mcma.backend.exception.BusinessException;
import vn.edu.usth.mcma.backend.repository.FoodRepository;
import vn.edu.usth.mcma.backend.service.FoodService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class FoodController {
    private final FoodService foodService;
    private final FoodRepository foodRepository;
    @PostMapping("/food")
    public ApiResponse createFood(@RequestBody FoodRequest request) {
        return foodService.createFood(request);
    }
    @GetMapping("/food")
    public List<Food> findAll(@RequestParam(required = false, defaultValue = "") String query, @PageableDefault Pageable pageable) {
        return foodService.findAll(query, pageable);
    }
    @GetMapping("/food/{id}")
    public Food findById(@PathVariable Long id) {
        return foodRepository
                .findById(id)
                .orElseThrow(() -> new BusinessException(ApiResponseCode.ENTITY_NOT_FOUND));
    }
    @PutMapping("/food/{id}")
    public ApiResponse updateFood(@PathVariable Long id, @RequestBody FoodRequest request) {
        return foodService.updateFood(id, request);
    }
    @DeleteMapping("/food/{id}")
    public ApiResponse deleteFood(@PathVariable Long id) {
        return foodService.deleteFood(id);
    }
}
