package com.spring.dto.Request.booking;

import com.spring.enums.SizeFoodOrDrink;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CouponRequest {
    private List<SizeFoodOrDrink> sizeFood;
    private List<SizeFoodOrDrink> sizeDrinks;
    private List<Integer> movieCouponIds;
    private List<Integer> userCouponIds;
}
