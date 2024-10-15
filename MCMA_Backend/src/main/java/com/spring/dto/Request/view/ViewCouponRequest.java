package com.spring.dto.Request.view;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ViewCouponRequest {
    private List<Integer> movieIds;
}
