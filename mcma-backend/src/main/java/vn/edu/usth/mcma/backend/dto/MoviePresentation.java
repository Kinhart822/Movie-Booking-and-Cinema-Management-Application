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
public class MoviePresentation {
    private Long id;
    private String name;
    private Integer length;
    private String description;
    private String publishDate;
    private String trailerUrl;
    private String poster;
    private String banner;

    private List<SchedulePresentation> schedules;
    private List<GenrePresentation> genres;
    private List<PerformerPresentation> performers;
    private RatingPresentation rating;
    private List<ReviewPresentation> reviews;
}
