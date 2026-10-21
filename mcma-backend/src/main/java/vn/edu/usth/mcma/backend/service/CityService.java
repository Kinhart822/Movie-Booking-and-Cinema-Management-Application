package vn.edu.usth.mcma.backend.service;

import java.time.Instant;
import java.util.List;

import jakarta.transaction.Transactional;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import vn.edu.usth.mcma.backend.common.constant.ApiResponseCode;
import vn.edu.usth.mcma.backend.common.enums.CommonStatus;

import vn.edu.usth.mcma.backend.dto.request.cinema.CityRequest;
import vn.edu.usth.mcma.backend.entity.City;
import vn.edu.usth.mcma.backend.common.dto.ApiResponse;
import vn.edu.usth.mcma.backend.exception.BusinessException;
import vn.edu.usth.mcma.backend.repository.CityRepository;
import vn.edu.usth.mcma.backend.security.jwt.JwtHelper;

import lombok.AllArgsConstructor;

@Transactional
@Service
@AllArgsConstructor
public class CityService {
    private final CityRepository cityRepository;
    private final JwtHelper jwtHelper;

    public ApiResponse createCity(CityRequest request) {
        Long userId = jwtHelper.getIdUserRequesting();
        City city = new City();
        city.setName(request.getName());
        city.setStatus(CommonStatus.ACTIVE.getStatus());
        city.setCreatedBy(userId);
        city.setLastModifiedBy(userId);
        cityRepository.save(city);
        return ApiResponse.ok();
    }

    public List<City> findAll(String query, Pageable pageable) {
        return cityRepository.findAllByNameContaining(query, pageable);
    }

    public City findById(Long id) {
        return cityRepository
                .findById(id)
                .orElseThrow(() -> new BusinessException(ApiResponseCode.ENTITY_NOT_FOUND));
    }

    public ApiResponse updateCity(Long id, CityRequest request) {
        Long userId = jwtHelper.getIdUserRequesting();
        City city =
                cityRepository
                        .findById(id)
                        .orElseThrow(() -> new BusinessException(ApiResponseCode.ENTITY_NOT_FOUND));
        city.setName(request.getName());
        city.setLastModifiedBy(userId);
        city.setLastModifiedDate(Instant.now());
        cityRepository.save(city);
        return ApiResponse.ok();
    }

    public ApiResponse deleteCity(Long id) {
        Long userId = jwtHelper.getIdUserRequesting();
        City city =
                cityRepository
                        .findById(id)
                        .orElseThrow(() -> new BusinessException(ApiResponseCode.ENTITY_NOT_FOUND));
        city.setStatus(CommonStatus.INACTIVE.getStatus());
        city.setLastModifiedBy(userId);
        city.setLastModifiedDate(Instant.now());
        cityRepository.save(city);
        return ApiResponse.ok();
    }
}
