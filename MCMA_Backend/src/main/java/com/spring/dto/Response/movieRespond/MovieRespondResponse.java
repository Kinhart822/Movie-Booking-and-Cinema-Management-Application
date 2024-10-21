package com.spring.dto.Response.movieRespond;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MovieRespondResponse {
    private String movieName;
    private String content;
    private Double ratingStar;
}
