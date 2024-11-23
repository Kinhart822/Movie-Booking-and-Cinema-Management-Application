package vn.edu.usth.mcma.backend.controller.admin;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import vn.edu.usth.mcma.backend.dao.Food;
import vn.edu.usth.mcma.backend.dto.FoodRequest;
import vn.edu.usth.mcma.backend.dto.CommonResponse;
import vn.edu.usth.mcma.backend.service.FoodService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class FoodController {
    private final FoodService foodService;
    @PostMapping("/food")
    public CommonResponse createFood(@RequestBody FoodRequest request, HttpServletRequest hsRequest) {
        return foodService.createFood(request, hsRequest);
    }
    @GetMapping("/food")
    public List<Food> findAll(@RequestParam String query, @PageableDefault Pageable pageable) {
        return foodService.findAll(query, pageable);
    }
    @GetMapping("/food/{id}")
    public Food findById(@PathVariable Long id) {
        return foodService.findById(id);
    }
    @PutMapping("/food/{id}")
    public CommonResponse updateFood(@PathVariable Long id, @RequestBody FoodRequest request, HttpServletRequest hsRequest) {
        return foodService.updateFood(id, request, hsRequest);
    }
    @DeleteMapping("/food/{id}")
    public CommonResponse deleteFood(@PathVariable Long id, HttpServletRequest hsRequest) {
        return foodService.deleteFood(id, hsRequest);
    }
}
