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

    private  List<String> comments;
    private  Double averageRating;
}
