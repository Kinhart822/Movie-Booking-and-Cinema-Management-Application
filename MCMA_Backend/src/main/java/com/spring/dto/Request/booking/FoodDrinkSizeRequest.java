package com.spring.dto.Request.booking;

import com.spring.enums.SizeFoodOrDrink;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FoodDrinkSizeRequest {
    private List<SizeFoodOrDrink> sizeFoodOrDrinks;
}
