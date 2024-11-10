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
    private List<Integer> seatIds;
    private List<String> unAvailableSeats;
    private List<String> unAvailableSeatsTypeList;
    private List<String> availableSeats;
    private List<String> availableSeatsTypeList;
}
