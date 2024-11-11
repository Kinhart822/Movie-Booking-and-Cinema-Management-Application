package com.spring.dto.response.booking.bookingSelected;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SelectedCinemaResponse {
    private Integer cinemaId;
    private String cinemaName;

    private List<String> screenType;
    private List<String> screenDescription;

    private List<String> movieSchedules;
}
