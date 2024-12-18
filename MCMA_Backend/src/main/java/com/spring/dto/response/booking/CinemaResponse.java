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
public class CinemaResponse {
    private String cityName;
    private Integer cinemaId;
    private String cinemaName;
    private String cinemaAddress;
    private List<String> screenType;
    private List<String> screenDescription;
    private List<String> foodName;
    private List<String> drinkName;
    private List<String> movieSchedules;
}
