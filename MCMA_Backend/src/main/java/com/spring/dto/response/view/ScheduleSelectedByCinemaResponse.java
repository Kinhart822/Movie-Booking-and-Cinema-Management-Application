package com.spring.dto.response.view;

import com.spring.enums.PerformerType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleSelectedByCinemaResponse {
    private String cinemaName;
    private List<Integer> scheduleId;
    private List<String> dayOfWeek;
    private List<String> date;
    private List<String> time;
}
