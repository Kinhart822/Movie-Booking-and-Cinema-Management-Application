package vn.edu.usth.mcma.frontend.dto.response;

import java.util.List;

import lombok.Data;
import vn.edu.usth.mcma.frontend.dto.response.BookingProcess.GenreResponse;

@Data
public class ComingSoonResponse {
    private Integer id;
    private String name;
    private Integer length;
    private String description;
    private String publishDate;
    private String trailerUrl;
    private String imageUrl;
    private String backgroundImageUrl;

    private List<GenreResponse> genreResponses;
    private List<PerformerResponse> performerResponses;
    private RatingResponse ratingResponse;
    private List<Review> reviews;
}
