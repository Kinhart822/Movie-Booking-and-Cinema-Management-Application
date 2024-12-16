package com.spring.dto.response.view;

import com.spring.enums.PerformerType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ComingSoonResponse {
    private Integer movieId;
    private String movieName;
    private Integer movieLength;
    private String publishedDate;
    private String imageUrl;
    private String backgroundImageUrl;
    private String trailer;
    private String description;
    private List<String> movieGenreNameList;
    private List<String> movieRatingDetailNameList;
    private List<String> moviePerformerNameList;
    private List<PerformerType> moviePerformerType;
}
