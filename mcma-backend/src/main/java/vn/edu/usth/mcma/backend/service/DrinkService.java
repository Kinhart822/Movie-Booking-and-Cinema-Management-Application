package vn.edu.usth.mcma.backend.service;

import constants.ApiResponseCode;
import constants.CommonStatus;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.edu.usth.mcma.backend.entity.Drink;
import vn.edu.usth.mcma.backend.dto.DrinkRequest;
import vn.edu.usth.mcma.backend.exception.ApiResponse;
import vn.edu.usth.mcma.backend.exception.BusinessException;
import vn.edu.usth.mcma.backend.repository.DrinkRepository;
import vn.edu.usth.mcma.backend.security.JwtHelper;

import java.time.Instant;
import java.util.List;

@Transactional
@Service
@AllArgsConstructor
public class DrinkService {
    private final DrinkRepository drinkRepository;
    private final JwtHelper jwtHelper;
    public ApiResponse createDrink(DrinkRequest request) {
        Long userId = jwtHelper.getIdUserRequesting();
        Drink drink = new Drink();
        drink.setName(request.getName());
        drink.setDescription(request.getDescription());
        drink.setImageBase64(request.getImageUrl());
//      debug  drink.setSize(request.getSize());
        drink.setVolume(request.getVolume());
//      debug  drink.setPrice(request.getPrice());
        drink.setStatus(CommonStatus.ACTIVE.getStatus());
        drink.setCreatedBy(userId);
        drink.setLastModifiedBy(userId);
        drinkRepository.save(drink);
        return ApiResponse.success();
    }
    public List<Drink> findAll(String query, Pageable pageable) {
        return drinkRepository.findAllByNameContaining(query, pageable);
    }
    public ApiResponse updateDrink(Long id, DrinkRequest request) {
        Long userId = jwtHelper.getIdUserRequesting();
        Drink drink = drinkRepository
                .findById(id)
                .orElseThrow(() -> new BusinessException(ApiResponseCode.ENTITY_NOT_FOUND));
        drink.setName(request.getName());
        drink.setDescription(request.getDescription());
        drink.setImageBase64(request.getImageUrl());
//      debug  drink.setSize(request.getSize());
        drink.setVolume(request.getVolume());
//      debug  drink.setPrice(request.getPrice());
        drink.setLastModifiedBy(userId);
        drink.setLastModifiedDate(Instant.now());
        drinkRepository.save(drink);
        return ApiResponse.success();
    }
    public ApiResponse deleteDrink(Long id) {
        Long userId = jwtHelper.getIdUserRequesting();
        Drink drink = drinkRepository
                .findById(id)
                .orElseThrow(() -> new BusinessException(ApiResponseCode.ENTITY_NOT_FOUND));
        drink.setStatus(CommonStatus.INACTIVE.getStatus());
        drink.setLastModifiedBy(userId);
        drink.setLastModifiedDate(Instant.now());
        drinkRepository.save(drink);
        return ApiResponse.success();
    }
}
