package com.spring.dto.request.booking;

import com.spring.enums.SizeFoodOrDrink;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FoodDrinkRequest {
    private List<Integer> foodIds;
    private List<Integer> drinkIds;
    private List<SizeFoodOrDrink> sizeFoodList;
    private List<SizeFoodOrDrink> sizeDrinks;
}

