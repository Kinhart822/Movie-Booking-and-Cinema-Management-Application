package com.spring.dto.Request.booking;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SeatRequest {
    private List<Integer> seatIds;
}
