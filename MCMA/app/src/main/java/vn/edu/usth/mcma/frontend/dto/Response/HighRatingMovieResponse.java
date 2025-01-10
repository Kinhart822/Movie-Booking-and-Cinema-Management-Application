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
    private String imageUrl;
    private String backgroundImageUrl;
    private List<Genre> genres;
    private Double avgVote;
}
