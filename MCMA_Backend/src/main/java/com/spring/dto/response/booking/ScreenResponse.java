package com.spring.dto.response.booking;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ScreenResponse {
    private String cinemaName;
    private Integer screenId;
    private String screenName;
    private String screenType;
    private String screenDescription;
}
