package com.spring.dto.response.booking;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HeldSeatResponse {
    private  String screenName;
    private Integer seatId;
    private String heldSeat;
    private String seatStatus;
    private Integer seatColumn;
    private Integer seatRow;
    private String heldSeatsType;
}
