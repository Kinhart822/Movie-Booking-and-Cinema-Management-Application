package com.spring.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SearchMovieByGenreResponse {
    private int id;
    private String name;
    private Integer length;
    private String imageUrl;
    private String trailerLink;
    private String datePublish;
    private String ratingName;
    private String ratingDescription;
    private String genreName;
    private String performerName;
    private String performerType;
    private String performerSex;
}

