package vn.edu.usth.mcma.backend.dto.movie;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShowtimeOfMovieByCinema {
    private Long cinemaId;
    private String cinemaName;
    private List<ShowtimeOfMovieByScreen> showtimeOfMovieByScreen;
}
