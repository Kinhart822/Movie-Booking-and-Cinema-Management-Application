package vn.edu.usth.mcma.frontend.dto.Response;

import java.util.List;

import lombok.Data;
import vn.edu.usth.mcma.frontend.dto.Response.BookingProcess.Genre;

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

    private List<Genre> genres;
    private List<Performer> performers;
    private Rating rating;
    private List<Review> reviews;
}
