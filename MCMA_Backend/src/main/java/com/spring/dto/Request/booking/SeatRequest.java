package com.spring.dto.Request.booking;

import com.spring.enums.SeatType;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SeatRequest {
    private List<Integer> seatIds;
    private List<SeatType> seatTypes;
}
