package com.spring.dto.Request.booking;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class ScheduleRequest {
    private Integer cinemaId;
    private Integer movieId;
    private LocalDate selectedDate;
    private LocalTime selectedTime;
}
