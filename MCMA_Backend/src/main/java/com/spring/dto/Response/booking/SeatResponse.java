package com.spring.dto.Response.booking;

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
    private List<String> selectedSeats;
    private List<String> selectedSeatTypeList;
}
