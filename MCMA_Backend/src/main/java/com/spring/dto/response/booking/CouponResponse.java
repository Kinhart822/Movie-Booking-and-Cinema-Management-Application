package com.spring.dto.response.booking;

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
public class CouponResponse {
    private List<Integer> couponIds;
    private List<String> couponNameList;
    private List<String> imageUrlList;
    private List<String> backgroundImageUrlList;
    private List<String> couponDescriptionList;
    private List<String> dateAvailableList;
    private List<String> expirationDates;
    private List<BigDecimal> discountRateList;
    private List<Integer> minSpendReqList;
    private List<Integer> discountLimitList;
    private List<Integer> pointToExchangeList;
}
