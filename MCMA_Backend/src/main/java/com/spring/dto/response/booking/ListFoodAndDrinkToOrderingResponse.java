package com.spring.dto.response.booking;

import com.spring.enums.SizeFoodOrDrink;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ListFoodAndDrinkToOrderingResponse {
    private String cinemaName;

    private List<Integer> foodIds;
    private List<String> foodNameList;
    private List<String> imageUrlFoodList;
    private List<String> descriptionFoodList;
    private List<SizeFoodOrDrink> sizeFoodList;

    private List<Integer> drinkIds;
    private List<String> drinkNameList;
    private List<String> imageUrlDrinkList;
    private List<String> descriptionDrinkList;
    private List<SizeFoodOrDrink> sizeDrinkList;
}
