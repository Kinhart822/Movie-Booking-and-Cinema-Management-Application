package vn.edu.usth.mcma.backend.controller.admin;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import vn.edu.usth.mcma.backend.entity.Drink;
import vn.edu.usth.mcma.backend.dto.CommonResponse;
import vn.edu.usth.mcma.backend.dto.DrinkRequest;
import vn.edu.usth.mcma.backend.service.DrinkService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class DrinkController {
    private final DrinkService drinkService;
    @PostMapping("/drink")
    public CommonResponse createDrink(@RequestBody DrinkRequest request, HttpServletRequest hsRequest) {
        return drinkService.createDrink(request, hsRequest);
    }
    @GetMapping("/drink")
    public List<Drink> findAll(@RequestParam String query, @PageableDefault Pageable pageable) {
        return drinkService.findAll(query, pageable);
    }
    @GetMapping("/drink/{id}")
    public Drink findById(@PathVariable Long id) {
        return drinkService.findById(id);
    }
    @PutMapping("/drink/{id}")
    public CommonResponse updateDrink(@PathVariable Long id, @RequestBody DrinkRequest request, HttpServletRequest hsRequest) {
        return drinkService.updateDrink(id, request, hsRequest);
    }
    @DeleteMapping("/drink/{id}")
    public CommonResponse deleteDrink(@PathVariable Long id, HttpServletRequest hsRequest) {
        return drinkService.deleteDrink(id, hsRequest);
    }
}
