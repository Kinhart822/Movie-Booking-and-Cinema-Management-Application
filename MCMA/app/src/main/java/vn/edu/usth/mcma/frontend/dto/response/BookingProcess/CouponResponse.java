package vn.edu.usth.mcma.frontend.dto.response.BookingProcess;

import java.math.BigDecimal;
import java.util.List;

import lombok.Data;

@Data
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

    public CouponResponse(List<Integer> couponIds, List<String> couponNameList, List<String> couponDescriptionList, List<BigDecimal> discountRateList, List<Integer> minSpendReqList, List<Integer> discountLimitList) {
        this.couponIds = couponIds;
        this.couponNameList = couponNameList;
        this.couponDescriptionList = couponDescriptionList;
        this.discountRateList = discountRateList;
        this.minSpendReqList = minSpendReqList;
        this.discountLimitList = discountLimitList;
    }
    public CouponResponse(List<Integer> couponIds, List<String> couponNameList, List<String> couponDescriptionList, List<BigDecimal> discountRateList, List<Integer> minSpendReqList, List<Integer> discountLimitList, List<Integer> pointToExchangeList) {
        this.couponIds = couponIds;
        this.couponNameList = couponNameList;
        this.couponDescriptionList = couponDescriptionList;
        this.discountRateList = discountRateList;
        this.minSpendReqList = minSpendReqList;
        this.discountLimitList = discountLimitList;
        this.pointToExchangeList = pointToExchangeList;
    }
}
