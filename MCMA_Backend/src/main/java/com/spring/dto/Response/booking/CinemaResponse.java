package com.spring.dto.Response.booking;

import com.spring.entities.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CinemaResponse {
    private String cinemaName;

    private List<String> screenType;
    private List<String> screenDescription;

    private List<String> foodName;

    private List<String> drinkName;

    private List<String> movieSchedules;
}
