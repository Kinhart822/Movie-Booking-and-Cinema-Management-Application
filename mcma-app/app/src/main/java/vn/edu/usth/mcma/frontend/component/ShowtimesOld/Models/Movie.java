package vn.edu.usth.mcma.frontend.component.ShowtimesOld.Models;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.edu.usth.mcma.frontend.dto.response.BookingProcess.GenreResponse;
import vn.edu.usth.mcma.frontend.dto.response.PerformerResponse;
import vn.edu.usth.mcma.frontend.dto.response.RatingResponse;
import vn.edu.usth.mcma.frontend.dto.response.Review;
import vn.edu.usth.mcma.frontend.dto.response.Schedule;

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
    private List<GenreResponse> genreResponses;
    private List<PerformerResponse> performerResponses;
    private RatingResponse ratingResponse;
    private List<Review> reviews;

    public Movie(String id, String title, List<String> showtimes) {
        this.id = id;
        this.title = title;
        this.showtimes = showtimes;
    }
}
