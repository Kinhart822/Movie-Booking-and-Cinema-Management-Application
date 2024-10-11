package com.spring.dto.Response.booking;

import com.spring.entities.Cinema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CityResponse {
    private String cityName;
    private List<String> cinemaNameList;
}
