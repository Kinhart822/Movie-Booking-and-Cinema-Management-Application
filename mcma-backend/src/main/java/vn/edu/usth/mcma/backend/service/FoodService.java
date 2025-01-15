package vn.edu.usth.mcma.backend.service;

import constants.ApiResponseCode;
import constants.CommonStatus;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.edu.usth.mcma.backend.entity.Food;
import vn.edu.usth.mcma.backend.dto.FoodRequest;
import vn.edu.usth.mcma.backend.exception.ApiResponse;
import vn.edu.usth.mcma.backend.exception.BusinessException;
import vn.edu.usth.mcma.backend.repository.FoodRepository;
import vn.edu.usth.mcma.backend.security.JwtHelper;

import java.time.Instant;
import java.util.List;

@Transactional
@Service
@AllArgsConstructor
public class FoodService {
    private final FoodRepository foodRepository;
    private final JwtHelper jwtHelper;
    public ApiResponse createFood(FoodRequest request) {
        Long userId = jwtHelper.getIdUserRequesting();
        Food food = new Food();
        food.setName(request.getName());
        food.setDescription(request.getDescription());
        food.setImageBase64(request.getImageUrl());
//      debug  food.setSize(request.getSize());
//      debug  food.setPrice(request.getPrice());
        food.setStatus(CommonStatus.ACTIVE.getStatus());
        food.setCreatedBy(userId);
        food.setLastModifiedBy(userId);
        foodRepository.save(food);
        return ApiResponse.success();
    }
    public List<Food> findAll(String query, Pageable pageable) {
        return foodRepository.findAllByNameContaining(query, pageable);
    }
    public ApiResponse updateFood(Long id, FoodRequest request) {
        Long userId = jwtHelper.getIdUserRequesting();
        Food food = foodRepository
                .findById(id)
                .orElseThrow(() -> new BusinessException(ApiResponseCode.ENTITY_NOT_FOUND));
        food.setName(request.getName());
        food.setDescription(request.getDescription());
        food.setImageBase64(request.getImageUrl());
//      debug  food.setSize(request.getSize());
//      debug  food.setPrice(request.getPrice());
        food.setLastModifiedBy(userId);
        food.setLastModifiedDate(Instant.now());
        foodRepository.save(food);
        return ApiResponse.success();
    }
    public ApiResponse deleteFood(Long id) {
        Long userId = jwtHelper.getIdUserRequesting();
        Food food = foodRepository
                .findById(id)
                .orElseThrow(() -> new BusinessException(ApiResponseCode.ENTITY_NOT_FOUND));
        food.setStatus(CommonStatus.INACTIVE.getStatus());
        food.setLastModifiedBy(userId);
        food.setLastModifiedDate(Instant.now());
        foodRepository.save(food);
        return ApiResponse.success();
    }
}
