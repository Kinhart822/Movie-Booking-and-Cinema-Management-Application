package com.spring.dto.Response.booking;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ScreenResponse {
    private String screenName;
    private String screenType;
    private String screenDescription;
}
