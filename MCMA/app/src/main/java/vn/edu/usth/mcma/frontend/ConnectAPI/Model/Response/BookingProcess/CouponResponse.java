package vn.edu.usth.mcma.frontend.ConnectAPI.Model.Response.BookingProcess;

import java.math.BigDecimal;
import java.util.List;

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

    public CouponResponse(List<Integer> couponIds, List<String> couponNameList, List<String> imageUrlList, List<String> backgroundImageUrlList, List<String> couponDescriptionList, List<BigDecimal> discountRateList, List<Integer> minSpendReqList, List<Integer> discountLimitList, List<Integer> pointToExchangeList) {
        this.couponIds = couponIds;
        this.couponNameList = couponNameList;
        this.imageUrlList = imageUrlList;
        this.backgroundImageUrlList = backgroundImageUrlList;
        this.couponDescriptionList = couponDescriptionList;
        this.discountRateList = discountRateList;
        this.minSpendReqList = minSpendReqList;
        this.discountLimitList = discountLimitList;
        this.pointToExchangeList = pointToExchangeList;
    }

    public CouponResponse(List<Integer> couponIds, List<String> couponNameList, List<String> imageUrlList, List<String> backgroundImageUrlList, List<String> couponDescriptionList, List<String> dateAvailableList, List<String> expirationDates, List<BigDecimal> discountRateList, List<Integer> minSpendReqList, List<Integer> discountLimitList, List<Integer> pointToExchangeList) {
        this.couponIds = couponIds;
        this.couponNameList = couponNameList;
        this.imageUrlList = imageUrlList;
        this.backgroundImageUrlList = backgroundImageUrlList;
        this.couponDescriptionList = couponDescriptionList;
        this.dateAvailableList = dateAvailableList;
        this.expirationDates = expirationDates;
        this.discountRateList = discountRateList;
        this.minSpendReqList = minSpendReqList;
        this.discountLimitList = discountLimitList;
        this.pointToExchangeList = pointToExchangeList;
    }

    public List<String> getDateAvailableList() {
        return dateAvailableList;
    }

    public void setDateAvailableList(List<String> dateAvailableList) {
        this.dateAvailableList = dateAvailableList;
    }

    public List<String> getExpirationDates() {
        return expirationDates;
    }

    public void setExpirationDates(List<String> expirationDates) {
        this.expirationDates = expirationDates;
    }

    public List<String> getBackgroundImageUrlList() {
        return backgroundImageUrlList;
    }

    public void setBackgroundImageUrlList(List<String> backgroundImageUrlList) {
        this.backgroundImageUrlList = backgroundImageUrlList;
    }

    public List<String> getImageUrlList() {
        return imageUrlList;
    }

    public void setImageUrlList(List<String> imageUrlList) {
        this.imageUrlList = imageUrlList;
    }

    public List<Integer> getPointToExchangeList() {
        return pointToExchangeList;
    }

    public void setPointToExchangeList(List<Integer> pointToExchangeList) {
        this.pointToExchangeList = pointToExchangeList;
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
