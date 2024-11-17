package com.spring.dto.request.respond;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MovieRespondRequest {
    private Integer movieId;
    private String comment;
    private Double selectedRatingStar;
}
