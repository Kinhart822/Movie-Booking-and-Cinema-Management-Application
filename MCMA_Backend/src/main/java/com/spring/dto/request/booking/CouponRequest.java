package com.spring.dto.request.booking;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CouponRequest {
    private Integer movieCouponId;
    private Integer userCouponId;
}
