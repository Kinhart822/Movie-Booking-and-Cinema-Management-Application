package com.spring.dto.Request.booking;

import com.spring.enums.SizeFoodOrDrink;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FoodDrinkRequest {
    private List<Integer> foodIds;
    private List<Integer> drinkIds;
    private List<SizeFoodOrDrink> sizeFood;
    private List<SizeFoodOrDrink> sizeDrinks;
}

