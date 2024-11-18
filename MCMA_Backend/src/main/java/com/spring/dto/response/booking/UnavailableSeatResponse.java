package com.spring.dto.response.booking;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UnavailableSeatResponse {
    private  String screenName;
    private Integer seatId;
    private String unAvailableSeat;
    private Integer seatColumn;
    private Integer seatRow;
    private String unAvailableSeatsType;
}
