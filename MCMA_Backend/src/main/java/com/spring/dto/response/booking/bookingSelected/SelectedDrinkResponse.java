package com.spring.dto.response.booking.bookingSelected;

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
public class SelectedDrinkResponse {
    private List<Integer> drinkIds;
    private List<String> drinkNameList;
    private List<String> imageUrlList;
    private List<String> descriptionList;
    private List<SizeFoodOrDrink> sizeDrinkList;
    private List<Integer> quantityList;
    private Double totalPrice;
}
