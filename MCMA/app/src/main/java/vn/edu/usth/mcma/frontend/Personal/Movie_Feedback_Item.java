package vn.edu.usth.mcma.frontend.Personal;

import lombok.Data;

@Data
public class Movie_Feedback_Item {
    private Long movieId;
    private String movie_name;
    private String movieGenreNameList;
    private String imageUrl;

    private String movie_type;
    private int movie_image;

    public Movie_Feedback_Item(Long movieId, String movie_name, String movieGenreNameList, String imageUrl) {
        this.movieId = movieId;
        this.movie_name = movie_name;
        this.movieGenreNameList = movieGenreNameList;
        this.imageUrl = imageUrl;
    }
}
