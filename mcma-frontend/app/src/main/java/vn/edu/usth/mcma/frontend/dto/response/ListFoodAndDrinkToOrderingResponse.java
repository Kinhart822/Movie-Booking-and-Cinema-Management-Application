package vn.edu.usth.mcma.frontend.dto.response;

import java.util.List;

import vn.edu.usth.mcma.frontend.constant.SizeFoodOrDrink;

public class ListFoodAndDrinkToOrderingResponse {
    private String cinemaName;

    private List<Integer> foodIds;
    private List<String> foodNameList;
    private List<String> imageUrlFoodList;
    private List<String> descriptionFoodList;
    private List<SizeFoodOrDrink> sizeFoodList;
    private List<Double> foodPriceList;

    private List<Integer> drinkIds;
    private List<String> drinkNameList;
    private List<String> imageUrlDrinkList;
    private List<String> descriptionDrinkList;
    private List<SizeFoodOrDrink> sizeDrinkList;
    private List<Double> drinkPriceList;

    public ListFoodAndDrinkToOrderingResponse(String cinemaName, List<String> descriptionDrinkList, List<String> descriptionFoodList, List<Integer> drinkIds, List<String> drinkNameList, List<Double> drinkPriceList, List<Integer> foodIds, List<String> foodNameList, List<Double> foodPriceList, List<String> imageUrlDrinkList, List<String> imageUrlFoodList, List<SizeFoodOrDrink> sizeDrinkList, List<SizeFoodOrDrink> sizeFoodList) {
        this.cinemaName = cinemaName;
        this.descriptionDrinkList = descriptionDrinkList;
        this.descriptionFoodList = descriptionFoodList;
        this.drinkIds = drinkIds;
        this.drinkNameList = drinkNameList;
        this.drinkPriceList = drinkPriceList;
        this.foodIds = foodIds;
        this.foodNameList = foodNameList;
        this.foodPriceList = foodPriceList;
        this.imageUrlDrinkList = imageUrlDrinkList;
        this.imageUrlFoodList = imageUrlFoodList;
        this.sizeDrinkList = sizeDrinkList;
        this.sizeFoodList = sizeFoodList;
    }

    public String getCinemaName() {
        return cinemaName;
    }

    public void setCinemaName(String cinemaName) {
        this.cinemaName = cinemaName;
    }

    public List<String> getDescriptionDrinkList() {
        return descriptionDrinkList;
    }

    public void setDescriptionDrinkList(List<String> descriptionDrinkList) {
        this.descriptionDrinkList = descriptionDrinkList;
    }

    public List<String> getDescriptionFoodList() {
        return descriptionFoodList;
    }

    public void setDescriptionFoodList(List<String> descriptionFoodList) {
        this.descriptionFoodList = descriptionFoodList;
    }

    public List<Integer> getDrinkIds() {
        return drinkIds;
    }

    public void setDrinkIds(List<Integer> drinkIds) {
        this.drinkIds = drinkIds;
    }

    public List<String> getDrinkNameList() {
        return drinkNameList;
    }

    public void setDrinkNameList(List<String> drinkNameList) {
        this.drinkNameList = drinkNameList;
    }

    public List<Double> getDrinkPriceList() {
        return drinkPriceList;
    }

    public void setDrinkPriceList(List<Double> drinkPriceList) {
        this.drinkPriceList = drinkPriceList;
    }

    public List<Integer> getFoodIds() {
        return foodIds;
    }

    public void setFoodIds(List<Integer> foodIds) {
        this.foodIds = foodIds;
    }

    public List<String> getFoodNameList() {
        return foodNameList;
    }

    public void setFoodNameList(List<String> foodNameList) {
        this.foodNameList = foodNameList;
    }

    public List<Double> getFoodPriceList() {
        return foodPriceList;
    }

    public void setFoodPriceList(List<Double> foodPriceList) {
        this.foodPriceList = foodPriceList;
    }

    public List<String> getImageUrlDrinkList() {
        return imageUrlDrinkList;
    }

    public void setImageUrlDrinkList(List<String> imageUrlDrinkList) {
        this.imageUrlDrinkList = imageUrlDrinkList;
    }

    public List<String> getImageUrlFoodList() {
        return imageUrlFoodList;
    }

    public void setImageUrlFoodList(List<String> imageUrlFoodList) {
        this.imageUrlFoodList = imageUrlFoodList;
    }

    public List<SizeFoodOrDrink> getSizeDrinkList() {
        return sizeDrinkList;
    }

    public void setSizeDrinkList(List<SizeFoodOrDrink> sizeDrinkList) {
        this.sizeDrinkList = sizeDrinkList;
    }

    public List<SizeFoodOrDrink> getSizeFoodList() {
        return sizeFoodList;
    }

    public void setSizeFoodList(List<SizeFoodOrDrink> sizeFoodList) {
        this.sizeFoodList = sizeFoodList;
    }
}
