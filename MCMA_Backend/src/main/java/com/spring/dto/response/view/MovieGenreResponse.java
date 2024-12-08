package com.spring.dto.response.view;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MovieGenreResponse {
    private Integer movieId;
    private String genreName;
    private String genreDescription;
    private String image_url;
}
