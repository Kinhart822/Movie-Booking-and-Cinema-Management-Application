package vn.edu.usth.mcma.frontend.Personal;

import java.util.List;

public class Movie_Feedback_Item {
    private Integer movieId;
    private String movie_name;
    private String movieGenreNameList;
    private String imageUrl;

    private String movie_type;
    private int movie_image;


    public Movie_Feedback_Item(String movie_name, String movie_type, int movie_image) {
        this.movie_name = movie_name;
        this.movie_type = movie_type;
        this.movie_image = movie_image;
    }

    public Movie_Feedback_Item(Integer movieId, String movie_name, String movieGenreNameList, String imageUrl) {
        this.movieId = movieId;
        this.movie_name = movie_name;
        this.movieGenreNameList = movieGenreNameList;
        this.imageUrl = imageUrl;
    }

    public Integer getMovieId() {
        return movieId;
    }

    public void setMovieId(Integer movieId) {
        this.movieId = movieId;
    }

    public String getMovieGenreNameList() {
        return movieGenreNameList;
    }

    public void setMovieGenreNameList(String movieGenreNameList) {
        this.movieGenreNameList = movieGenreNameList;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getMovie_name() {
        return movie_name;
    }

    public void setMovie_name(String movie_name) {
        this.movie_name = movie_name;
    }

    public String getMovie_type() {
        return movie_type;
    }

    public void setMovie_type(String movie_type) {
        this.movie_type = movie_type;
    }

    public int getMovie_image() {
        return movie_image;
    }

    public void setMovie_image(int movie_image) {
        this.movie_image = movie_image;
    }
}
