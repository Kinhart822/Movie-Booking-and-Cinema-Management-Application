package vn.edu.usth.mcma.frontend.dto.movie;

import java.util.List;

import lombok.Data;

@Data
public class ShowtimeOfMovieByCinema {
    private Long cinemaId;
    private String cinemaName;
    private List<ShowtimeOfMovieByScreen> showtimeOfMovieByScreen;
}
