package com.spring.dto.Response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SearchMovieByNameResponse {
    private int id;
    private String name;
    private Integer length;
    private String trailerLink;
    private Date datePublish;
    private String ratingName;
    private String ratingDescription;
}
