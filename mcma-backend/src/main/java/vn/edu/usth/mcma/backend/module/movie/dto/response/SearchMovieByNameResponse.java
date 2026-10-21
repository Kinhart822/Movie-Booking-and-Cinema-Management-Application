package vn.edu.usth.mcma.backend.module.movie.dto.response;

import java.util.List;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SearchMovieByNameResponse {
    private Long id;
    private String name;
    private String description;
    private String poster;
    private String banner;
    private Integer length; // in seconds
    private String publishDate;
    private String trailerUrl;
    private RatingPresentation rating;
    private List<GenrePresentation> genres;
    private List<PerformerPresentation> performers;
}
