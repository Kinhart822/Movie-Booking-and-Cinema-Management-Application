package vn.edu.usth.mcma.backend.controller.admin;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.usth.mcma.backend.dto.concession.ConcessionRequest;
import vn.edu.usth.mcma.backend.dto.concession.ConcessionResponse;
import vn.edu.usth.mcma.backend.entity.Concession;
import vn.edu.usth.mcma.backend.entity.Drink;
import vn.edu.usth.mcma.backend.entity.Food;
import vn.edu.usth.mcma.backend.exception.ApiResponse;
import vn.edu.usth.mcma.backend.service.ConcessionService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin")
@Tag(name = "Concession Management")
public class ConcessionController {
    private final ConcessionService concessionService;

    @Operation(summary = "Find all available concessions, can be filtered by cinema", description = "If cinemaId is null, return all concessions. Else return concessions available by that cinema.")
    @GetMapping("/concession")
    public ResponseEntity<List<Concession>> findAllConcession(@RequestParam(required = false) Long cinemaId) {
        return ResponseEntity.ok(concessionService.findAllConcession(cinemaId));
    }
    @Operation(summary = "Create a concession", description = "Create a combination of food and drink")
    @PostMapping("/concession")
    public ResponseEntity<ApiResponse> createConcession(@RequestBody ConcessionRequest request) {
        return ResponseEntity.ok(concessionService.createConcession(request));
    }
    @Operation(summary = "Find detail of a concession", description = "Including items and their quantity")
    @GetMapping("/concession/{concessionId}")
    public ResponseEntity<ConcessionResponse> findConcessionById(@PathVariable Long concessionId) {
        return ResponseEntity.ok(concessionService.findConcessionById(concessionId));
    }
    @Operation(summary = "", description = "")
    @PutMapping("/concession/{concessionId}")
    public ResponseEntity<ApiResponse> updateConcession(@PathVariable Long concessionId, ConcessionRequest request) {
        return ResponseEntity.ok(concessionService.updateConcession(concessionId, request));
    }
    @Operation(summary = "", description = "")
    @GetMapping("/concession/food")
    public ResponseEntity<List<Food>> findAllFood(@RequestParam(required = false, defaultValue = "") String query) {
        return ResponseEntity.ok(concessionService.findAllFood(query));
    }
    @Operation(summary = "", description = "")
    @GetMapping("/concession/food/{foodId}")
    public ResponseEntity<Food> findFoodById(@PathVariable Long foodId) {
        return ResponseEntity.ok(concessionService.findFoodById(foodId));
    }
    @Operation(summary = "", description = "")
    @GetMapping("/concession/drink")
    public ResponseEntity<List<Drink>> findAllDrink(@RequestParam(required = false, defaultValue = "") String query) {
        return ResponseEntity.ok(concessionService.findAllDrink(query));
    }
    @Operation(summary = "", description = "")
    @GetMapping("/concession/drink/{drinkId}")
    public ResponseEntity<Drink> findDrinkById(@PathVariable Long drinkId) {
        return ResponseEntity.ok(concessionService.findDrinkById(drinkId));
    }
}
