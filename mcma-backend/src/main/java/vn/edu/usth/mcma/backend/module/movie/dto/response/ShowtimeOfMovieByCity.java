package vn.edu.usth.mcma.backend.module.movie.dto.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShowtimeOfMovieByCity {
    private Long cityId;
    private String cityName;
    private List<ShowtimeOfMovieByCinema> showtimeOfMovieByCinema;
}
