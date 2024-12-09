package com.spring.dto.response.booking;

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
    private Integer movieId;
    private String imageUrlMovie;
    private String cityName;
    private String cinemaName;
    private String startDateTime;
    private String endDateTime;
    private String screenName;
    private List<String> ticketTypeName;
    private List<String> seatName;
    private List<String> foodNameList;
    private List<String> drinkNameList;

    private Double totalPrice;
    private String status;
}
