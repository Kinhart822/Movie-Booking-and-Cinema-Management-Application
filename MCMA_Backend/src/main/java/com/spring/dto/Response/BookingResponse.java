package com.spring.dto.Response;

import com.spring.enums.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookingResponse {
    private String bookingNo;
    private String movieName;
    private String startDateTime;
    private String endDateTime;
    private PaymentMethod paymentMethod;
    private BookingStatus status;

    private List<Integer> selectedTickets;

    private List<Integer> selectedSeat;
    private List<SeatType> seatTypes;

    private List<Integer> selectedFoods;
    private List<Integer> selectedDrinks;
    private List<SizeFoodOrDrink> sizeFood;
    private List<SizeFoodOrDrink> sizeDrinks;

    private List<Integer> availableMovieCoupons;
    private List<Integer> selectedMovieCoupons;

    private List<Integer> availableUserCoupons;
    private List<Integer> selectedUserCoupons;

    private Double totalPrice;
}
