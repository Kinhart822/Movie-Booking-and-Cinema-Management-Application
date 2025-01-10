package vn.edu.usth.mcma.frontend.components.Showtimes.Models;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.edu.usth.mcma.frontend.dto.Response.BookingProcess.Genre;
import vn.edu.usth.mcma.frontend.dto.Response.Performer;
import vn.edu.usth.mcma.frontend.dto.Response.Rating;
import vn.edu.usth.mcma.frontend.dto.Response.Review;
import vn.edu.usth.mcma.frontend.dto.Response.Schedule;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Movie implements Serializable {
    private String id;
    private String title;
    private List<String> showtimes;

    private Long movieId;
    private String movieName;
    private Integer movieLength;
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

    public Movie(String id, String title, List<String> showtimes) {
        this.id = id;
        this.title = title;
        this.showtimes = showtimes;
    }
}
