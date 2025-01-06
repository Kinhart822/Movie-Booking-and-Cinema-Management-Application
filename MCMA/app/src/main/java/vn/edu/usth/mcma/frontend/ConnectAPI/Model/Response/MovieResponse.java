package vn.edu.usth.mcma.frontend.ConnectAPI.Model.Response;

import java.util.List;

import lombok.Data;
import vn.edu.usth.mcma.frontend.ConnectAPI.Enum.PerformerSex;
import vn.edu.usth.mcma.frontend.ConnectAPI.Enum.PerformerType;
import vn.edu.usth.mcma.frontend.ConnectAPI.Model.Response.BookingProcess.Genre;

@Data
public class MovieResponse {
    private Long id;
    private String name;
    private Integer length;
    private String description;
    private String publishDate;
    private String trailerUrl;
    private String imageUrl;
    private String backgroundImageUrl;

    private List<Schedule> schedules;
    private List<Genre> genres;
    private List<Performer> performers;
    private Rating rating;
    private List<Review> reviews;
}
