package vn.edu.usth.mcma.frontend.model;

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
