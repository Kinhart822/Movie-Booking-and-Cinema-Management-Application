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
public class HighRatingMovieResponse {
    private Integer movieId;
    private String movieName;
    private Integer movieLength;
    private String publishedDate;
    private String imageUrl;
    private List<String> movieGenreNameList;
}
