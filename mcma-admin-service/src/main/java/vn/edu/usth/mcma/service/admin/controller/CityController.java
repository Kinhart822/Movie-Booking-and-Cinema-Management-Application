package vn.edu.usth.mcma.service.admin.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import vn.edu.usth.mcma.service.admin.dto.CityRequest;
import vn.edu.usth.mcma.service.admin.service.CityService;
import vn.edu.usth.mcma.service.common.CommonResponse;
import vn.edu.usth.mcma.service.common.dao.City;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class CityController {
    private final CityService cityService;
    @PostMapping("/city")
    public CommonResponse createCity(@RequestBody CityRequest request) {
        return cityService.createCity(request);
    }
    @GetMapping("/city")
    public List<City> findAll() {
        return cityService.findAll();
    }
    @GetMapping("/city/{id}")
    public City findById(@PathVariable Long id) {
        return cityService.findById(id);
    }
    @PutMapping("/city/{id}")
    public CommonResponse updateCity(@PathVariable Long id, @RequestBody CityRequest request) {
        return cityService.updateCity(id, request);
    }
    @DeleteMapping("/city/{id}")
    public CommonResponse deleteCity(@PathVariable Long id) {
        return cityService.deleteCity(id);
    }
}
