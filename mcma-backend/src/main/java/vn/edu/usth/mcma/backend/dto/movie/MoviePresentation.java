package vn.edu.usth.mcma.backend.dto.movie;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.edu.usth.mcma.backend.dto.unsorted.ReviewPresentation;
import vn.edu.usth.mcma.backend.dto.unsorted.SchedulePresentation;

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
