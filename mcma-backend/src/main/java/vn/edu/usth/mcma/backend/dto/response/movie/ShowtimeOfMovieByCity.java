package vn.edu.usth.mcma.backend.dto.response.movie;

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
