package com.spring.dto.request.movieRespond;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MovieRespondRequest {
    private Integer movieId;
    private String comment;
    private Double selectedRatingStar;
}
