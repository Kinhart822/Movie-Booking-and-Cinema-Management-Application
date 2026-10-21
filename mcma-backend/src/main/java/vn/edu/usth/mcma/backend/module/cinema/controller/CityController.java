package vn.edu.usth.mcma.backend.module.cinema.controller;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import vn.edu.usth.mcma.backend.common.dto.ApiResponse;
import vn.edu.usth.mcma.backend.module.cinema.dto.request.CityRequest;
import vn.edu.usth.mcma.backend.module.cinema.entity.City;
import vn.edu.usth.mcma.backend.module.cinema.service.CityService;

import lombok.RequiredArgsConstructor;

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
    public List<City> findAll(
            @RequestParam(required = false, defaultValue = "") String query,
            @PageableDefault Pageable pageable) {
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
