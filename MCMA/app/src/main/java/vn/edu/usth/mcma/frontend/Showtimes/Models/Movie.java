package vn.edu.usth.mcma.frontend.Showtimes.Models;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.edu.usth.mcma.frontend.ConnectAPI.Enum.PerformerSex;
import vn.edu.usth.mcma.frontend.ConnectAPI.Enum.PerformerType;
import vn.edu.usth.mcma.frontend.ConnectAPI.Model.Response.BookingProcess.Genre;

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
    private List<String> dayOfWeek;
    private List<String> date;
    private List<String> time;

    private List<Genre> genres;

    private List<String> moviePerformerNameList;
    private List<String> moviePerformerDobList;
    private List<PerformerSex> moviePerformerSex;
    private List<PerformerType> moviePerformerType;

    private List<String> movieRatingDetailNameList;
    private List<String> movieRatingDetailDescriptions;

    private List<String> comments;
    private Double averageRating;

    public Movie(String id, String title, List<String> showtimes) {
        this.id = id;
        this.title = title;
        this.showtimes = showtimes;
    }

    public Movie(Long movieId, String title, String movieName, Integer movieLength, String description, String publishDate, String trailerUrl, String imageUrl, String backgroundImageUrl, List<String> time, List<Genre> genres, List<String> moviePerformerNameList, List<PerformerType> moviePerformerType, List<String> movieRatingDetailNameList, List<String> movieRatingDetailDescriptions, List<String> comments, Double averageRating) {
        this.title = title;
        this.averageRating = averageRating;
        this.comments = comments;
        this.description = description;
        this.imageUrl = imageUrl;
        this.backgroundImageUrl = backgroundImageUrl;
        this.time = time;
        this.genres = genres;
        this.movieId = movieId;
        this.movieLength = movieLength;
        this.movieName = movieName;
        this.moviePerformerNameList = moviePerformerNameList;
        this.moviePerformerType = moviePerformerType;
        this.movieRatingDetailDescriptions = movieRatingDetailDescriptions;
        this.movieRatingDetailNameList = movieRatingDetailNameList;
        this.publishDate = publishDate;
        this.trailerUrl = trailerUrl;
    }
}
