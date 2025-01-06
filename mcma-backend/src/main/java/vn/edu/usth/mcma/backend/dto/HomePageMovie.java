package vn.edu.usth.mcma.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HomePageMovie {
    private Long id;
    private String name;
    private Integer length;
    private String description;
    private String publishDate;
    private String trailerUrl;
    private String imageUrl;
    private String backgroundImageUrl;

    private List<GenrePresentation> genres;
    private List<PerformerPresentation> performers;
    private RatingPresentation rating;
    private List<ReviewPresentation> reviews;
}
