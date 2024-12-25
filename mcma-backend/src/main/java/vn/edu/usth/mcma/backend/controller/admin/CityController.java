package vn.edu.usth.mcma.backend.controller.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import vn.edu.usth.mcma.backend.dto.CityRequest;
import vn.edu.usth.mcma.backend.exception.ApiResponse;
import vn.edu.usth.mcma.backend.service.CityService;
import vn.edu.usth.mcma.backend.entity.City;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class CityController {
    private final CityService cityService;
    @PostMapping("/city")
    public ApiResponse createCity(@RequestBody CityRequest request) {
        return cityService.createCity(request);
    }
    @GetMapping("/city")
    public List<City> findAll(@RequestParam String query, @PageableDefault Pageable pageable) {
        return cityService.findAll(query, pageable);
    }
    @GetMapping("/city/{id}")
    public City findById(@PathVariable Long id) {
        return cityService.findById(id);
    }
    @PutMapping("/city/{id}")
    public ApiResponse updateCity(@PathVariable Long id, @RequestBody CityRequest request) {
        return cityService.updateCity(id, request);
    }
    @DeleteMapping("/city/{id}")
    public ApiResponse deleteCity(@PathVariable Long id) {
        return cityService.deleteCity(id);
    }
}
