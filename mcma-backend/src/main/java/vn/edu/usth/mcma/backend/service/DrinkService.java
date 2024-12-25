package vn.edu.usth.mcma.backend.service;

import constants.CommonStatus;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.edu.usth.mcma.backend.entity.Drink;
import vn.edu.usth.mcma.backend.dto.DrinkRequest;
import vn.edu.usth.mcma.backend.exception.ApiResponse;
import vn.edu.usth.mcma.backend.repository.DrinkRepository;
import vn.edu.usth.mcma.backend.security.JwtUtil;

import java.time.Instant;
import java.util.List;

@Transactional
@Service
public class DrinkService extends AbstractService<Drink, Long> {
    private final DrinkRepository drinkRepository;
    private final JwtUtil jwtUtil;

    public DrinkService(DrinkRepository drinkRepository, JwtUtil jwtUtil) {
        super(drinkRepository);
        this.drinkRepository = drinkRepository;
        this.jwtUtil = jwtUtil;
    }
    public ApiResponse createDrink(DrinkRequest request) {
        Long userId = jwtUtil.getUserIdFromToken();
        Drink drink = new Drink();
        drink.setName(request.getName());
        drink.setDescription(request.getDescription());
        drink.setImageUrl(request.getImageUrl());
        drink.setSize(request.getSize());
        drink.setVolume(request.getVolume());
        drink.setPrice(request.getPrice());
        drink.setStatus(CommonStatus.ACTIVE.getStatus());
        drink.setCreatedBy(userId);
        drink.setLastModifiedBy(userId);
        drinkRepository.save(drink);
        return this.successResponse();
    }
    public List<Drink> findAll(String query, Pageable pageable) {
        return drinkRepository.findAllByNameContaining(query, pageable);
    }
    public ApiResponse updateDrink(Long id, DrinkRequest request) {
        Long userId = jwtUtil.getUserIdFromToken();
        Drink drink = findById(id);
        drink.setName(request.getName());
        drink.setDescription(request.getDescription());
        drink.setImageUrl(request.getImageUrl());
        drink.setSize(request.getSize());
        drink.setVolume(request.getVolume());
        drink.setPrice(request.getPrice());
        drink.setLastModifiedBy(userId);
        drink.setLastModifiedDate(Instant.now());
        drinkRepository.save(drink);
        return this.successResponse();
    }
    public ApiResponse deleteDrink(Long id) {
        Long userId = jwtUtil.getUserIdFromToken();
        Drink drink = findById(id);
        drink.setStatus(CommonStatus.DELETED.getStatus());
        drink.setLastModifiedBy(userId);
        drink.setLastModifiedDate(Instant.now());
        drinkRepository.save(drink);
        return this.successResponse();
    }
}
