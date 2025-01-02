package vn.edu.usth.mcma.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SearchMovieByNameResponse {
    private Integer id;
    private String name;
    private Integer length;
    private String description;
    private String imageUrl;
    private String backgroundImageUrl;
    private String trailerLink;
    private String datePublish;
    private List<String> ratingNameList;
    private List<String> ratingDescriptionList;
    private List<String> genreNameList;
    private List<String> performerNameList;
    private List<String> performerType;
    private List<String> performerSex;
}

