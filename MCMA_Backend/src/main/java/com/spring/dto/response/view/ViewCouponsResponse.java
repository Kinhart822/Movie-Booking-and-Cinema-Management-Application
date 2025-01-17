package com.spring.dto.response.view;

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
    private List<Integer> couponIds;
    private List<String> nameCoupons;
    private List<String> imageUrlCoupons;
    private List<String> backgroundImageUrlList;
    private List<String> descriptionCoupons;
    private List<String> dateAvailableList;
    private List<String> expirationDates;
    private List<BigDecimal> discount;
    private List<Integer> minSpendReqList;
    private List<String> discountLimit;
    private List<Integer> pointToExchangeList;
}
