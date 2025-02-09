package vn.edu.usth.mcma.backend.dto.movie;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MovieDetail {
    private Long id;
    private String name;
    private Integer length;
    private String overview;
    private Instant releaseDate;
    private String trailerUrl;
    private String poster;
    private String banner;

    private List<String> genres;
    private List<String> directors;
    private List<String> actors;
    private String rating;
    private Double avgVotes;
}
