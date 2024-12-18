package com.spring.dto.response.view;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ViewCinemaResponse {
    private List<Integer> cinemaIdList;
    private List<String> cinemaNameList;
    private List<String> cityNameList;
    private List<String> cinemaAddressList;
}
