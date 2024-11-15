package com.spring.dto.response.booking.bookingSelected;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SelectedScreenResponse {
    private Integer screenId;
    private String screenName;
    private String screenType;
    private String screenDescription;
    private Double screenPrice;
}
