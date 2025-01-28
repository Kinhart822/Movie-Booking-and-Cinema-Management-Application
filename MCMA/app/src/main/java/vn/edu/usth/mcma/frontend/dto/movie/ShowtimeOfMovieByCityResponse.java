package vn.edu.usth.mcma.frontend.dto.movie;

import java.util.List;

import lombok.Data;

@Data
public class ShowtimeOfMovieByCityResponse {
    private Long cityId;
    private String cityName;
    private List<ShowtimeOfMovieByCinemaResponse> showtimeOfMovieByCinemaResponse;
}
