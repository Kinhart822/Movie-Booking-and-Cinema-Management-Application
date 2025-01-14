package vn.edu.usth.mcma.frontend.dto.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import vn.edu.usth.mcma.frontend.dto.response.BookingProcess.Genre;

@Data
@AllArgsConstructor
public class SearchMovieByNameResponse {
    private Long id;
    private String name;
    private String description;
    private String imageUrl;
    private String backgroundImageUrl;
    private Integer length;
    private String publishDate;
    private String trailerUrl;
    private Rating rating;
    private List<Genre> genres;
    private List<Performer> performers;
}
