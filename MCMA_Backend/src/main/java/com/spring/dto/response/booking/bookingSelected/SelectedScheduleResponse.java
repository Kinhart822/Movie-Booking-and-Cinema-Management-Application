package com.spring.dto.response.booking.bookingSelected;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SelectedScheduleResponse {
    private Integer scheduleId;
    private String selectedDate;
    private String selectedTime;
}
