package vn.edu.usth.mcma.frontend.dto.response;

import java.util.List;

import lombok.Data;
import vn.edu.usth.mcma.frontend.dto.response.BookingProcess.GenreResponse;

@Data
public class HighRatingMovieResponse {
    private Integer id;
    private String name;
    private Integer length;
    private String publishDate;
    private String imageBase64;
    private String imageBackgroundBase64;
    private List<GenreResponse> genreResponses;
    private Double avgVote;
}
