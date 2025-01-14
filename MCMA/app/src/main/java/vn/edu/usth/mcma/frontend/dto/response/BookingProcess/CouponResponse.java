package vn.edu.usth.mcma.frontend.dto.response.BookingProcess;

import java.math.BigDecimal;
import java.util.List;

public class CouponResponse {
    private List<Integer> couponIds;
    private List<String> couponNameList;
    private List<String> couponDescriptionList;
    private List<BigDecimal> discountRateList;
    private List<Integer> minSpendReqList;
    private List<Integer> discountLimitList;

    public CouponResponse(List<Integer> couponIds, List<String> couponNameList, List<String> couponDescriptionList, List<BigDecimal> discountRateList, List<Integer> minSpendReqList, List<Integer> discountLimitList) {
        this.couponIds = couponIds;
        this.couponNameList = couponNameList;
        this.couponDescriptionList = couponDescriptionList;
        this.discountRateList = discountRateList;
        this.minSpendReqList = minSpendReqList;
        this.discountLimitList = discountLimitList;
    }

    public List<Integer> getCouponIds() {
        return couponIds;
    }

    public void setCouponIds(List<Integer> couponIds) {
        this.couponIds = couponIds;
    }

    public List<String> getCouponNameList() {
        return couponNameList;
    }

    public void setCouponNameList(List<String> couponNameList) {
        this.couponNameList = couponNameList;
    }

    public List<String> getCouponDescriptionList() {
        return couponDescriptionList;
    }

    public void setCouponDescriptionList(List<String> couponDescriptionList) {
        this.couponDescriptionList = couponDescriptionList;
    }

    public List<BigDecimal> getDiscountRateList() {
        return discountRateList;
    }

    public void setDiscountRateList(List<BigDecimal> discountRateList) {
        this.discountRateList = discountRateList;
    }

    public List<Integer> getMinSpendReqList() {
        return minSpendReqList;
    }

    public void setMinSpendReqList(List<Integer> minSpendReqList) {
        this.minSpendReqList = minSpendReqList;
    }

    public List<Integer> getDiscountLimitList() {
        return discountLimitList;
    }

    public void setDiscountLimitList(List<Integer> discountLimitList) {
        this.discountLimitList = discountLimitList;
    }
}
