package vn.edu.usth.mcma.backend.service;

import constants.ApiResponseCode;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.edu.usth.mcma.backend.dto.DrinkRequest;
import vn.edu.usth.mcma.backend.dto.FoodRequest;
import vn.edu.usth.mcma.backend.dto.concession.*;
import vn.edu.usth.mcma.backend.entity.*;
import vn.edu.usth.mcma.backend.exception.ApiResponse;
import vn.edu.usth.mcma.backend.exception.BusinessException;
import vn.edu.usth.mcma.backend.repository.*;
import vn.edu.usth.mcma.backend.security.JwtHelper;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Transactional
@Service
@AllArgsConstructor
public class ConcessionService {

    private final ConcessionRepository concessionRepository;
    private final JwtHelper jwtHelper;
    private final DrinkRepository drinkRepository;
    private final FoodRepository foodRepository;
    private final ConcessionFoodRepository concessionFoodRepository;
    private final ConcessionDrinkRepository concessionDrinkRepository;

    /*
     * Concession
     */
    public List<Concession> findAllConcession(Long cinemaId) {
        return concessionRepository.findAllByCinemaId(cinemaId);
    }

    public ApiResponse createConcession(ConcessionRequest request) {
        Long userId = jwtHelper.getIdUserRequesting();
        Instant now = Instant.now();
        List<Long> foodRequestIds = request.getFoods().stream().map(ConcessionFoodRequest::getFoodId).toList();
        List<Long> drinkRequestIds = request.getDrinks().stream().map(ConcessionDrinkRequest::getDrinkId).toList();
        List<Food> existingFoods = foodRepository
                .findAllById(foodRequestIds);
        List<Drink> existingDrinks = drinkRepository
                .findAllById(drinkRequestIds);
        if (existingFoods.size() < request.getFoods().size()) {
            List<Long> foodNotFoundIds = new ArrayList<>(foodRequestIds);
            foodNotFoundIds.removeAll(existingFoods.stream().map(Food::getId).toList());
            List<Long> drinkNotFoundIds = new ArrayList<>(drinkRequestIds);
            drinkNotFoundIds.removeAll(existingDrinks.stream().map(Drink::getId).toList());
            throw new BusinessException(ApiResponseCode.ENTITY_NOT_FOUND.setDescription(String.format("Foods not found: %s\nDrinks not found: %s", foodNotFoundIds, drinkNotFoundIds)));
        }
        Concession concession = concessionRepository.save(Concession.builder()
                .name(request.getName())
                .description(request.getDescription())
                .comboPrice(request.getComboPrice())
                .imageBase64(request.getImageBase64())
                .createdBy(userId)
                .createdDate(now)
                .lastModifiedBy(userId)
                .lastModifiedDate(now).build());
        //todo consider extract
        Map<Long, Food> foodIdFoodMap = new HashMap<>();
        Map<Long, Drink> drinkIdDrinkMap = new HashMap<>();
        existingFoods.forEach(food -> foodIdFoodMap.put(food.getId(), food));
        existingDrinks.forEach(drink -> drinkIdDrinkMap.put(drink.getId(), drink));
        concessionFoodRepository.saveAll(request.getFoods().stream()
                .map(foodRequest -> ConcessionFood.builder()
                        .id(ConcessionFoodPK.builder()
                                .concession(concession)
                                .food(foodIdFoodMap.get(foodRequest.getFoodId()))
                                .build())
                        .quantity(foodRequest.getQuantity())
                        .build()).toList());
        concessionDrinkRepository.saveAll(request.getDrinks().stream()
                .map(drinkRequest -> ConcessionDrink.builder()
                        .id(ConcessionDrinkPK.builder()
                                .concession(concession)
                                .drink(drinkIdDrinkMap.get(drinkRequest.getDrinkId()))
                                .build())
                        .quantity(drinkRequest.getQuantity())
                        .build()).toList());
        return ApiResponse.ok();
    }

    public ApiResponse updateConcession(Long concessionId, ConcessionRequest request) {
        Long userId = jwtHelper.getIdUserRequesting();
        Instant now = Instant.now();
        concessionRepository.save(concessionRepository
                .findById(concessionId)
                .orElseThrow(() -> new BusinessException(ApiResponseCode.ENTITY_NOT_FOUND)).toBuilder()
                .name(request.getName())
                .description(request.getDescription())
                .comboPrice(request.getComboPrice())
                .imageBase64(request.getImageBase64())
                .lastModifiedBy(userId)
                .lastModifiedDate(now).build());
        return ApiResponse.ok();
    }

    public ConcessionResponse findConcessionById(Long concessionId) {
        Concession concession = concessionRepository
                .findById(concessionId)
                .orElseThrow(() -> new BusinessException(ApiResponseCode.ENTITY_NOT_FOUND.setDescription(String.format("Concession not found: %d", concessionId))));
        List<ConcessionFood> concessionFoods = concessionFoodRepository.findAllByConcessionId(concessionId);
        List<ConcessionDrink> concessionDrinks = concessionDrinkRepository.findAllByConcessionId(concessionId);
        return ConcessionResponse.builder()
                .id(concession.getId())
                .name(concession.getName())
                .description(concession.getDescription())
                .comboPrice(concession.getComboPrice())
                .imageBase64(concession.getImageBase64())
                .foods(concessionFoods.stream()
                        .map(cf -> FoodResponse.builder()
                                .id(cf.getId().getFood().getId())
                                .name(cf.getId().getFood().getName())
                                .description(cf.getId().getFood().getDescription())
                                .unitPrice(cf.getId().getFood().getUnitPrice())
                                .quantity(cf.getQuantity()).build()).toList())
                .drinks(concessionDrinks.stream()
                        .map(cd -> DrinkResponse.builder()
                                .id(cd.getId().getDrink().getId())
                                .name(cd.getId().getDrink().getName())
                                .description(cd.getId().getDrink().getDescription())
                                .unitPrice(cd.getId().getDrink().getUnitPrice())
                                .quantity(cd.getQuantity()).build()).toList())
                .build();
    }

    /*
     * Drink
     */
    public ApiResponse createDrink(DrinkRequest request) {
        Long userId = jwtHelper.getIdUserRequesting();
        Instant now = Instant.now();
        Drink drink = new Drink();
        drink.setName(request.getName());
        drink.setDescription(request.getDescription());
        drink.setUnitPrice(request.getUnitPrice());
        drink.setCreatedBy(userId);
        drink.setCreatedDate(now);
        drink.setLastModifiedBy(userId);
        drink.setLastModifiedDate(now);
        drinkRepository.save(drink);
        return ApiResponse.ok();
    }
    public List<Drink> findAllDrink(String query) {
        return drinkRepository.findAllByNameContaining(query);
    }
    public ApiResponse updateDrink(Long drinkId, DrinkRequest request) {
        Long userId = jwtHelper.getIdUserRequesting();
        Drink drink = drinkRepository
                .findById(drinkId)
                .orElseThrow(() -> new BusinessException(ApiResponseCode.ENTITY_NOT_FOUND));
        drink.setName(request.getName());
        drink.setDescription(request.getDescription());
        drink.setUnitPrice(request.getUnitPrice());
        drink.setLastModifiedBy(userId);
        drink.setLastModifiedDate(Instant.now());
        drinkRepository.save(drink);
        return ApiResponse.ok();
    }
    public ApiResponse deleteDrink(Long drinkId) {
        Long userId = jwtHelper.getIdUserRequesting();
        Drink drink = drinkRepository
                .findById(drinkId)
                .orElseThrow(() -> new BusinessException(ApiResponseCode.ENTITY_NOT_FOUND));
        drink.setLastModifiedBy(userId);
        drink.setLastModifiedDate(Instant.now());
        drinkRepository.save(drink);
        return ApiResponse.ok();
    }
    /*
     * Food
     */
    public ApiResponse createFood(FoodRequest request) {
        Long userId = jwtHelper.getIdUserRequesting();
        Instant now = Instant.now();
        Food food = new Food();
        food.setName(request.getName());
        food.setDescription(request.getDescription());
        food.setUnitPrice(request.getUnitPrice());
        food.setCreatedBy(userId);
        food.setCreatedDate(now);
        food.setLastModifiedBy(userId);
        food.setLastModifiedDate(now);
        foodRepository.save(food);
        return ApiResponse.ok();
    }
    public List<Food> findAllFood(String query) {
        return foodRepository.findAllByNameContaining(query);
    }
    public ApiResponse updateFood(Long foodId, FoodRequest request) {
        Long userId = jwtHelper.getIdUserRequesting();
        Food food = foodRepository
                .findById(foodId)
                .orElseThrow(() -> new BusinessException(ApiResponseCode.ENTITY_NOT_FOUND));
        food.setName(request.getName());
        food.setDescription(request.getDescription());
        food.setUnitPrice(request.getUnitPrice());
        food.setLastModifiedBy(userId);
        food.setLastModifiedDate(Instant.now());
        foodRepository.save(food);
        return ApiResponse.ok();
    }
    public ApiResponse deleteFood(Long foodId) {
        Long userId = jwtHelper.getIdUserRequesting();
        Food food = foodRepository
                .findById(foodId)
                .orElseThrow(() -> new BusinessException(ApiResponseCode.ENTITY_NOT_FOUND));
        food.setLastModifiedBy(userId);
        food.setLastModifiedDate(Instant.now());
        foodRepository.save(food);
        return ApiResponse.ok();
    }

    public Food findFoodById(Long foodId) {
        return foodRepository
                .findById(foodId)
                .orElseThrow(() -> new BusinessException(ApiResponseCode.ENTITY_NOT_FOUND));
    }
    public Drink findDrinkById(Long drinkId) {
        return drinkRepository
                .findById(drinkId)
                .orElseThrow(() -> new BusinessException(ApiResponseCode.ENTITY_NOT_FOUND));
    }
}
