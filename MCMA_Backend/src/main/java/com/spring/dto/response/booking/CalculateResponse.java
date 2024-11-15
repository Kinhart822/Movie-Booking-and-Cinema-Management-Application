package com.spring.dto.response.booking;

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
    private List<String> nameCoupons;
    private List<String> descriptionList;
    private List<Double> discountList;
    private List<Double> discountLimitList;
    private List<Double> minSpendReqList;
    private List<String> dateAvailableList;
    private Double totalPrice;
}
