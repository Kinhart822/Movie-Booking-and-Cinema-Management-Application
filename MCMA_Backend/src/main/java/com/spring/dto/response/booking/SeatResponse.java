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
public class SeatResponse {
    private  String screenName;
    private List<String> unAvailableSeats;
    private List<String> unAvailableSeatsTypeList;
    private Integer seatId;
    private String availableSeat;
    private Integer seatColumn;
    private Integer seatRow;
    private String availableSeatsType;
    private List<String> heldSeats;
    private List<String> heldSeatsTypeList;
}
