package com.spring.dto.Response.booking;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CalculateResponse {
    private List<Integer> availableMovieCouponIds;
    private List<Integer> availableUserCouponIds;
    private double totalPrice;
}
