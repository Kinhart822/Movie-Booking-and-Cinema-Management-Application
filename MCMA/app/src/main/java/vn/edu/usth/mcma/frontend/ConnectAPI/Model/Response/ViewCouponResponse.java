package vn.edu.usth.mcma.frontend.ConnectAPI.Model.Response;

import java.math.BigDecimal;
import java.util.List;

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

    public ViewCouponResponse(List<Integer> couponIds, List<String> nameCoupons, List<String> imageUrlCoupons, List<String> backgroundImageUrlList, List<String> descriptionCoupons, List<String> dateAvailableList, List<String> expirationDates, List<BigDecimal> discount, List<Integer> minSpendReqList, List<String> discountLimit, List<Integer> pointToExchangeList) {
        this.couponIds = couponIds;
        this.nameCoupons = nameCoupons;
        this.imageUrlCoupons = imageUrlCoupons;
        this.backgroundImageUrlList = backgroundImageUrlList;
        this.descriptionCoupons = descriptionCoupons;
        this.dateAvailableList = dateAvailableList;
        this.expirationDates = expirationDates;
        this.discount = discount;
        this.minSpendReqList = minSpendReqList;
        this.discountLimit = discountLimit;
        this.pointToExchangeList = pointToExchangeList;
    }

    public List<Integer> getCouponIds() {
        return couponIds;
    }

    public void setCouponIds(List<Integer> couponIds) {
        this.couponIds = couponIds;
    }

    public List<String> getNameCoupons() {
        return nameCoupons;
    }

    public void setNameCoupons(List<String> nameCoupons) {
        this.nameCoupons = nameCoupons;
    }

    public List<String> getImageUrlCoupons() {
        return imageUrlCoupons;
    }

    public void setImageUrlCoupons(List<String> imageUrlCoupons) {
        this.imageUrlCoupons = imageUrlCoupons;
    }

    public List<String> getBackgroundImageUrlList() {
        return backgroundImageUrlList;
    }

    public void setBackgroundImageUrlList(List<String> backgroundImageUrlList) {
        this.backgroundImageUrlList = backgroundImageUrlList;
    }

    public List<String> getDescriptionCoupons() {
        return descriptionCoupons;
    }

    public void setDescriptionCoupons(List<String> descriptionCoupons) {
        this.descriptionCoupons = descriptionCoupons;
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

    public List<BigDecimal> getDiscount() {
        return discount;
    }

    public void setDiscount(List<BigDecimal> discount) {
        this.discount = discount;
    }

    public List<Integer> getMinSpendReqList() {
        return minSpendReqList;
    }

    public void setMinSpendReqList(List<Integer> minSpendReqList) {
        this.minSpendReqList = minSpendReqList;
    }

    public List<String> getDiscountLimit() {
        return discountLimit;
    }

    public void setDiscountLimit(List<String> discountLimit) {
        this.discountLimit = discountLimit;
    }

    public List<Integer> getPointToExchangeList() {
        return pointToExchangeList;
    }

    public void setPointToExchangeList(List<Integer> pointToExchangeList) {
        this.pointToExchangeList = pointToExchangeList;
    }
}
