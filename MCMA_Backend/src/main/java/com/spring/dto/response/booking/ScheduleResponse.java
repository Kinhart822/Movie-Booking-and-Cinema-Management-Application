package com.spring.dto.response.booking;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleResponse {
    private String movieName;
    private String cinemaName;
    private String screenName;
    private Integer scheduleId;
    private String date;
    private String time;
}
