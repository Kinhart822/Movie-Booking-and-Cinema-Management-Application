package com.spring.dto.request.booking;

import com.spring.enums.PaymentMethod;
import com.spring.enums.SizeFoodOrDrink;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BookingRequest {
    private Integer bookingId;
    private Integer movieId;
    private Integer cityId;
    private Integer cinemaId;
    private Integer screenId;
    private Integer scheduleId;
    private List<Integer> ticketIds;
    private List<Integer> seatIds;
    private List<Integer> foodIds;
//    private List<SizeFoodOrDrink> sizeFoodList;
    private List<Integer> drinkIds;
//    private List<SizeFoodOrDrink> sizeDrinkList;
    private Integer movieCouponId;
    private Integer userCouponId;
    private PaymentMethod paymentMethod;
}
