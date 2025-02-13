package vn.edu.usth.mcma.backend.admin.dto.movie;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MovieShortResponse {
    private Long id;
    private String name;
    private String poster;
    private Integer length;
    private Instant releaseDate;
    private String trailerYoutubeId;
    private Integer status;
}
