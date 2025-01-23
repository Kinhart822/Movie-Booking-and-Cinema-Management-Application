package vn.edu.usth.mcma.frontend.dto.response;

import java.math.BigDecimal;
import java.util.List;

import lombok.Data;

@Data
public class ViewCouponResponse {
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
