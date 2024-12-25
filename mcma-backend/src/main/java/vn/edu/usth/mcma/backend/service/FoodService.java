package vn.edu.usth.mcma.backend.service;

import constants.CommonStatus;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.edu.usth.mcma.backend.entity.Food;
import vn.edu.usth.mcma.backend.dto.FoodRequest;
import vn.edu.usth.mcma.backend.exception.ApiResponse;
import vn.edu.usth.mcma.backend.repository.FoodRepository;
import vn.edu.usth.mcma.backend.security.JwtUtil;

import java.time.Instant;
import java.util.List;

@Transactional
@Service
public class FoodService extends AbstractService<Food, Long> {
    private final FoodRepository foodRepository;
    private final JwtUtil jwtUtil;

    public FoodService(FoodRepository foodRepository, JwtUtil jwtUtil) {
        super(foodRepository);
        this.foodRepository = foodRepository;
        this.jwtUtil = jwtUtil;
    }
    public ApiResponse createFood(FoodRequest request) {
        Long userId = jwtUtil.getUserIdFromToken();
        Food food = new Food();
        food.setName(request.getName());
        food.setDescription(request.getDescription());
        food.setImageUrl(request.getImageUrl());
        food.setSize(request.getSize());
        food.setPrice(request.getPrice());
        food.setStatus(CommonStatus.ACTIVE.getStatus());
        food.setCreatedBy(userId);
        food.setLastModifiedBy(userId);
        foodRepository.save(food);
        return this.successResponse();
    }
    public List<Food> findAll(String query, Pageable pageable) {
        return foodRepository.findAllByNameContaining(query, pageable);
    }
    public ApiResponse updateFood(Long id, FoodRequest request) {
        Long userId = jwtUtil.getUserIdFromToken();
        Food food = findById(id);
        food.setName(request.getName());
        food.setDescription(request.getDescription());
        food.setImageUrl(request.getImageUrl());
        food.setSize(request.getSize());
        food.setPrice(request.getPrice());
        food.setLastModifiedBy(userId);
        food.setLastModifiedDate(Instant.now());
        foodRepository.save(food);
        return this.successResponse();
    }
    public ApiResponse deleteFood(Long id) {
        Long userId = jwtUtil.getUserIdFromToken();
        Food food = findById(id);
        food.setStatus(CommonStatus.DELETED.getStatus());
        food.setLastModifiedBy(userId);
        food.setLastModifiedDate(Instant.now());
        foodRepository.save(food);
        return this.successResponse();
    }
}
