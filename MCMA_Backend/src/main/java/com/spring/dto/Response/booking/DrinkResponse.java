package com.spring.dto.Response.booking;

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
public class DrinkResponse {
    private List<String> drinkNameList;
    private List<String> descriptionList;
    private List<SizeFoodOrDrink> sizeDrinkList;
}
