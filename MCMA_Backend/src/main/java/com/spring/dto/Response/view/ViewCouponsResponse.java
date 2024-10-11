package com.spring.dto.Response.view;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ViewCouponsResponse {
    private List<String> nameCoupons;
    private List<String> descriptionCoupons;
    private List<String> expirationDates;
    private List<BigDecimal> discount;
    private List<String> discountLimit;
}
