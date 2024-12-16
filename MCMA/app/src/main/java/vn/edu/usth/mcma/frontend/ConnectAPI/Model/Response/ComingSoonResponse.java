package vn.edu.usth.mcma.frontend.ConnectAPI.Model.Response;

import java.util.List;

import vn.edu.usth.mcma.frontend.ConnectAPI.Enum.PerformerType;

public class ComingSoonResponse {
    private Integer movieId;
    private String movieName;
    private Integer movieLength;
    private String publishedDate;
    private String imageUrl;
    private String backgroundImageUrl;
    private String trailer;
    private String description;
    private List<String> movieGenreNameList;
    private List<String> movieRatingDetailNameList;
    private List<String> moviePerformerNameList;
    private List<PerformerType> moviePerformerType;

    public ComingSoonResponse(String backgroundImageUrl, String description, String imageUrl, List<String> movieGenreNameList, Integer movieId, Integer movieLength, String movieName, List<String> moviePerformerNameList, List<PerformerType> moviePerformerType, List<String> movieRatingDetailNameList, String publishedDate, String trailer) {
        this.backgroundImageUrl = backgroundImageUrl;
        this.description = description;
        this.imageUrl = imageUrl;
        this.movieGenreNameList = movieGenreNameList;
        this.movieId = movieId;
        this.movieLength = movieLength;
        this.movieName = movieName;
        this.moviePerformerNameList = moviePerformerNameList;
        this.moviePerformerType = moviePerformerType;
        this.movieRatingDetailNameList = movieRatingDetailNameList;
        this.publishedDate = publishedDate;
        this.trailer = trailer;
    }

    public String getBackgroundImageUrl() {
        return backgroundImageUrl;
    }

    public void setBackgroundImageUrl(String backgroundImageUrl) {
        this.backgroundImageUrl = backgroundImageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public Integer getMovieId() {
        return movieId;
    }

    public void setMovieId(Integer movieId) {
        this.movieId = movieId;
    }

    public Integer getMovieLength() {
        return movieLength;
    }

    public void setMovieLength(Integer movieLength) {
        this.movieLength = movieLength;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public List<String> getMoviePerformerNameList() {
        return moviePerformerNameList;
    }

    public void setMoviePerformerNameList(List<String> moviePerformerNameList) {
        this.moviePerformerNameList = moviePerformerNameList;
    }

    public List<PerformerType> getMoviePerformerType() {
        return moviePerformerType;
    }

    public void setMoviePerformerType(List<PerformerType> moviePerformerType) {
        this.moviePerformerType = moviePerformerType;
    }

    public List<String> getMovieRatingDetailNameList() {
        return movieRatingDetailNameList;
    }

    public void setMovieRatingDetailNameList(List<String> movieRatingDetailNameList) {
        this.movieRatingDetailNameList = movieRatingDetailNameList;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }

    public String getTrailer() {
        return trailer;
    }

    public void setTrailer(String trailer) {
        this.trailer = trailer;
    }
}
