package com.spring.dto.request.booking;

import com.spring.enums.SizeFoodOrDrink;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class EditBookingRequest {
    private Integer movieId;
    private Integer cityId;
    private Integer cinemaId;
    private Integer screenId;
    private Integer scheduleId;
    private Integer seatId;
    private List<Integer> ticketIds;
    private List<Integer> seatIds;
    private List<Integer> foodIds;
    private List<SizeFoodOrDrink> sizeFoodList;
    private List<Integer> drinkIds;
    private List<SizeFoodOrDrink> sizeDrinks;
    private List<Integer> movieCouponIds;
    private List<Integer> userCouponIds;}
