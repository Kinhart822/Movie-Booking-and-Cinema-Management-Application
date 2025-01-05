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
public class AvailableSeatResponse {
    private String screenName;
    private Integer seatId;
    private String availableSeat;
    private String seatStatus;
    private Integer seatColumn;
    private Integer seatRow;
    private String availableSeatsType;
    private Double seatPrice;
}
