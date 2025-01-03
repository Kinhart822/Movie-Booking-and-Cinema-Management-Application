package com.spring.dto.response.booking;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SendBookingResponse {
    private Integer bookingId;
    private String movieName;
    private String cityName;
    private String cinemaName;
    private String startDateTime;
    private String endDateTime;
    private String screenName;
    private List<String> ticketTypeName;
    private List<String> seatName;
    private List<String> foodNameList = new ArrayList<>();
    private List<String> drinkNameList = new ArrayList<>();
    private List<String> couponName = new ArrayList<>();
    private Double totalPrice;
    private String bookingStatus;
}
