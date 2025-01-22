package vn.edu.usth.mcma.frontend.dto.response;

import java.util.List;

import lombok.Data;
import vn.edu.usth.mcma.frontend.dto.response.BookingProcess.GenreResponse;

@Data
public class NowShowingResponse {
    private Long id;
    private String name;
    private Integer length;
    private String description;
    private String publishDate;
    private String trailerUrl;
    private String imageBase64;
    private String backgroundImageBase64;

    private List<GenreResponse> genres;
    private List<PerformerResponse> performers;
    private RatingResponse rating;
    private List<Review> reviews;
}
