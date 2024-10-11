package com.spring.dto.Response.booking;

import com.spring.enums.SeatType;
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
    private List<String> unAvailableSeats;
    private List<String> availableSeats;
    private List<SeatType> seatTypeList;
}
