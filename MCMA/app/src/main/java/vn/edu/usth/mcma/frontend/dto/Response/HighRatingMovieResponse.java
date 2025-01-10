package vn.edu.usth.mcma.frontend.dto.Response;

import java.util.List;

import lombok.Data;
import vn.edu.usth.mcma.frontend.dto.Response.BookingProcess.Genre;

@Data
public class HighRatingMovieResponse {
    private Integer id;
    private String name;
    private Integer length;
    private String publishDate;
    private String imageBase64;
    private String imageBackgroundBase64;
    private List<Genre> genres;
    private Double avgVote;
}
