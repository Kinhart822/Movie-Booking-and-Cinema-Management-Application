package vn.edu.usth.mcma.backend.controller.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import vn.edu.usth.mcma.backend.entity.Drink;
import vn.edu.usth.mcma.backend.dto.DrinkRequest;
import vn.edu.usth.mcma.backend.exception.ApiResponse;
import vn.edu.usth.mcma.backend.service.DrinkService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class DrinkController {
    private final DrinkService drinkService;
    @PostMapping("/drink")
    public ApiResponse createDrink(@RequestBody DrinkRequest request) {
        return drinkService.createDrink(request);
    }
    @GetMapping("/drink")
    public List<Drink> findAll(@RequestParam(required = false, defaultValue = "") String query, @PageableDefault Pageable pageable) {
        return drinkService.findAll(query, pageable);
    }
    @GetMapping("/drink/{id}")
    public Drink findById(@PathVariable Long id) {
        return drinkService.findById(id);
    }
    @PutMapping("/drink/{id}")
    public ApiResponse updateDrink(@PathVariable Long id, @RequestBody DrinkRequest request) {
        return drinkService.updateDrink(id, request);
    }
    @DeleteMapping("/drink/{id}")
    public ApiResponse deleteDrink(@PathVariable Long id) {
        return drinkService.deleteDrink(id);
    }
}
