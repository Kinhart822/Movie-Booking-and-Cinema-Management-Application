package vn.edu.usth.mcma.frontend.Home;

import java.util.List;

public class NowShowing_Item {
    private Integer movieId;
    private String movieName;
    private Integer movieLength;
    private String publishedDate;
    private String imageUrl;
    private List<String> movieGenreNameList;
    private List<String> movieRatingDetailNameList;

    public NowShowing_Item(Integer movieId, String movieName, Integer movieLength, String publishedDate, String imageUrl, List<String> movieGenreNameList, List<String> movieRatingDetailNameList) {
        this.movieId = movieId;
        this.movieName = movieName;
        this.movieLength = movieLength;
        this.publishedDate = publishedDate;
        this.imageUrl = imageUrl;
        this.movieGenreNameList = movieGenreNameList;
        this.movieRatingDetailNameList = movieRatingDetailNameList;
    }

    public Integer getMovieId() {
        return movieId;
    }

    public void setMovieId(Integer movieId) {
        this.movieId = movieId;
    }

    public List<String> getMovieRatingDetailNameList() {
        return movieRatingDetailNameList;
    }

    public void setMovieRatingDetailNameList(List<String> movieRatingDetailNameList) {
        this.movieRatingDetailNameList = movieRatingDetailNameList;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public Integer getMovieLength() {
        return movieLength;
    }

    public void setMovieLength(Integer movieLength) {
        this.movieLength = movieLength;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public List<String> getMovieGenreNameList() {
        return movieGenreNameList;
    }

    public void setMovieGenreNameList(List<String> movieGenreNameList) {
        this.movieGenreNameList = movieGenreNameList;
    }
}
