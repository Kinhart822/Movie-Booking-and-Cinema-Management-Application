package com.spring.dto.Response.movieRespond;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RatingResponse {
    private String movieName;
    private Double ratingStar;
}
