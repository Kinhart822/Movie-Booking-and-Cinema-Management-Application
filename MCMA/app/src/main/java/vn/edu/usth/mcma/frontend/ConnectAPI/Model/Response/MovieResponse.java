package vn.edu.usth.mcma.frontend.ConnectAPI.Model.Response;

import java.util.List;

import vn.edu.usth.mcma.frontend.ConnectAPI.Enum.PerformerSex;
import vn.edu.usth.mcma.frontend.ConnectAPI.Enum.PerformerType;

public class MovieResponse {
    private Integer movieId;
    private String movieName;
    private Integer movieLength;
    private String publishedDate;
    private String trailerLink;
    private String imageUrl;

    private List<String> movieGenreNameList;
    private List<String> imageUrlList;
    private List<String> movieGenreDescriptions;

    private List<String> moviePerformerNameList;
    private List<String> moviePerformerDobList;
    private List<PerformerSex> moviePerformerSex;
    private List<PerformerType> moviePerformerType;
    private List<String> movieRatingDetailNameList;
    private List<String> movieRatingDetailDescriptions;

    private  List<String> comments;
    private  Double averageRating;

    public MovieResponse(Double averageRating, List<String> comments, String imageUrl, List<String> imageUrlList, List<String> movieGenreDescriptions, List<String> movieGenreNameList, Integer movieId, Integer movieLength, String movieName, List<String> moviePerformerDobList, List<String> moviePerformerNameList, List<PerformerSex> moviePerformerSex, List<PerformerType> moviePerformerType, List<String> movieRatingDetailDescriptions, List<String> movieRatingDetailNameList, String publishedDate, String trailerLink) {
        this.averageRating = averageRating;
        this.comments = comments;
        this.imageUrl = imageUrl;
        this.imageUrlList = imageUrlList;
        this.movieGenreDescriptions = movieGenreDescriptions;
        this.movieGenreNameList = movieGenreNameList;
        this.movieId = movieId;
        this.movieLength = movieLength;
        this.movieName = movieName;
        this.moviePerformerDobList = moviePerformerDobList;
        this.moviePerformerNameList = moviePerformerNameList;
        this.moviePerformerSex = moviePerformerSex;
        this.moviePerformerType = moviePerformerType;
        this.movieRatingDetailDescriptions = movieRatingDetailDescriptions;
        this.movieRatingDetailNameList = movieRatingDetailNameList;
        this.publishedDate = publishedDate;
        this.trailerLink = trailerLink;
    }

    public Double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(Double averageRating) {
        this.averageRating = averageRating;
    }

    public List<String> getComments() {
        return comments;
    }

    public void setComments(List<String> comments) {
        this.comments = comments;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public List<String> getImageUrlList() {
        return imageUrlList;
    }

    public void setImageUrlList(List<String> imageUrlList) {
        this.imageUrlList = imageUrlList;
    }

    public List<String> getMovieGenreDescriptions() {
        return movieGenreDescriptions;
    }

    public void setMovieGenreDescriptions(List<String> movieGenreDescriptions) {
        this.movieGenreDescriptions = movieGenreDescriptions;
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

    public List<String> getMoviePerformerDobList() {
        return moviePerformerDobList;
    }

    public void setMoviePerformerDobList(List<String> moviePerformerDobList) {
        this.moviePerformerDobList = moviePerformerDobList;
    }

    public List<String> getMoviePerformerNameList() {
        return moviePerformerNameList;
    }

    public void setMoviePerformerNameList(List<String> moviePerformerNameList) {
        this.moviePerformerNameList = moviePerformerNameList;
    }

    public List<PerformerSex> getMoviePerformerSex() {
        return moviePerformerSex;
    }

    public void setMoviePerformerSex(List<PerformerSex> moviePerformerSex) {
        this.moviePerformerSex = moviePerformerSex;
    }

    public List<PerformerType> getMoviePerformerType() {
        return moviePerformerType;
    }

    public void setMoviePerformerType(List<PerformerType> moviePerformerType) {
        this.moviePerformerType = moviePerformerType;
    }

    public List<String> getMovieRatingDetailDescriptions() {
        return movieRatingDetailDescriptions;
    }

    public void setMovieRatingDetailDescriptions(List<String> movieRatingDetailDescriptions) {
        this.movieRatingDetailDescriptions = movieRatingDetailDescriptions;
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

    public String getTrailerLink() {
        return trailerLink;
    }

    public void setTrailerLink(String trailerLink) {
        this.trailerLink = trailerLink;
    }
}
