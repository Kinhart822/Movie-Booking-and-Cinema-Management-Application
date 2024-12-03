package vn.edu.usth.mcma.backend.service;

import constants.EntityStatus;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.edu.usth.mcma.backend.entity.Drink;
import vn.edu.usth.mcma.backend.dto.CommonResponse;
import vn.edu.usth.mcma.backend.dto.DrinkRequest;
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
    public CommonResponse createDrink(DrinkRequest request, HttpServletRequest hsRequest) {
        Long userId = jwtUtil.getUserIdFromToken(hsRequest);
        Drink drink = new Drink();
        drink.setName(request.getName());
        drink.setDescription(request.getDescription());
        drink.setImageUrl(request.getImageUrl());
        drink.setSize(request.getSize());
        drink.setVolume(request.getVolume());
        drink.setPrice(request.getPrice());
        drink.setStatus(EntityStatus.CREATED.getStatus());
        drink.setCreatedBy(userId);
        drink.setLastModifiedBy(userId);
        drinkRepository.save(drink);
        return this.successResponse();
    }
    public List<Drink> findAll(String query, Pageable pageable) {
        return drinkRepository.findAllByNameContaining(query, pageable);
    }
    public CommonResponse updateDrink(Long id, DrinkRequest request, HttpServletRequest hsRequest) {
        Long userId = jwtUtil.getUserIdFromToken(hsRequest);
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
    public CommonResponse deleteDrink(Long id, HttpServletRequest hsRequest) {
        Long userId = jwtUtil.getUserIdFromToken(hsRequest);
        Drink drink = findById(id);
        drink.setStatus(EntityStatus.DELETED.getStatus());
        drink.setLastModifiedBy(userId);
        drink.setLastModifiedDate(Instant.now());
        drinkRepository.save(drink);
        return this.successResponse();
    }
}
