package vn.edu.usth.mcma.backend.module.concession.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import vn.edu.usth.mcma.backend.common.dto.ApiResponse;
import vn.edu.usth.mcma.backend.module.concession.dto.request.ConcessionRequest;
import vn.edu.usth.mcma.backend.module.concession.dto.response.ConcessionResponse;
import vn.edu.usth.mcma.backend.module.concession.entity.Concession;
import vn.edu.usth.mcma.backend.module.concession.entity.Drink;
import vn.edu.usth.mcma.backend.module.concession.entity.Food;
import vn.edu.usth.mcma.backend.module.concession.service.ConcessionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin")
@Tag(name = "Concession Management")
public class ConcessionController {
    private final ConcessionService concessionService;

    @Operation(
            summary = "Find all available concessions, can be filtered by cinema",
            description =
                    "If cinemaId is null, return all concessions. Else return concessions available by that cinema.")
    @GetMapping("/concession")
    public ResponseEntity<List<Concession>> findAllConcession(
            @RequestParam(required = false) @NonNull Long cinemaId) {
        return ResponseEntity.ok(concessionService.findAllConcession(cinemaId));
    }

    @Operation(
            summary = "Create a concession",
            description = "Create a combination of food and drink")
    @PostMapping("/concession")
    public ResponseEntity<ApiResponse> createConcession(@RequestBody ConcessionRequest request) {
        return ResponseEntity.ok(concessionService.createConcession(request));
    }

    @Operation(
            summary = "Find detail of a concession",
            description = "Including items and their quantity")
    @GetMapping("/concession/{concessionId}")
    public ResponseEntity<ConcessionResponse> findConcessionById(
            @PathVariable @NonNull Long concessionId) {
        return ResponseEntity.ok(concessionService.findConcessionById(concessionId));
    }

    @Operation(summary = "Update a concession", description = "Update concession detail")
    @PutMapping("/concession/{concessionId}")
    public ResponseEntity<ApiResponse> updateConcession(
            @PathVariable @NonNull Long concessionId, @RequestBody ConcessionRequest request) {
        return ResponseEntity.ok(concessionService.updateConcession(concessionId, request));
    }

    @Operation(summary = "Find all food items")
    @GetMapping("/concession/food")
    public ResponseEntity<List<Food>> findAllFood(
            @RequestParam(required = false, defaultValue = "") String query) {
        return ResponseEntity.ok(concessionService.findAllFood(query));
    }

    @Operation(summary = "Find food by ID")
    @GetMapping("/concession/food/{foodId}")
    public ResponseEntity<Food> findFoodById(@PathVariable @NonNull Long foodId) {
        return ResponseEntity.ok(concessionService.findFoodById(foodId));
    }

    @Operation(summary = "Find all drink items")
    @GetMapping("/concession/drink")
    public ResponseEntity<List<Drink>> findAllDrink(
            @RequestParam(required = false, defaultValue = "") String query) {
        return ResponseEntity.ok(concessionService.findAllDrink(query));
    }

    @Operation(summary = "Find drink by ID")
    @GetMapping("/concession/drink/{drinkId}")
    public ResponseEntity<Drink> findDrinkById(@PathVariable @NonNull Long drinkId) {
        return ResponseEntity.ok(concessionService.findDrinkById(drinkId));
    }
}
