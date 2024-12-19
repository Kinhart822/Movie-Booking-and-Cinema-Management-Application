package vn.edu.usth.mcma.frontend.ConnectAPI.Model.Response;

import java.util.List;

import vn.edu.usth.mcma.frontend.ConnectAPI.Enum.PerformerType;

public class ScheduleResponse {
    private Integer movieId;
    private String movieName;

    private Integer movieLength;
    private String description;
    private String publishedDate;
    private String trailerLink;
    private String imageUrl;
    private String backgroundImAageUrl;

    private List<String> movieGenreNameList;
    private List<String> imageUrlList;
    private List<String> movieGenreDescriptions;

    private List<String> moviePerformerNameList;
    private List<PerformerType> moviePerformerType;

    private List<String> movieRatingDetailNameList;
    private List<String> movieRatingDetailDescriptions;

    private  List<String> comments;
    private  Double averageRating;

    private String cinemaName;
    private List<String> screenName;
    private List<Integer> scheduleId;
    private List<String> dayOfWeek;
    private List<String> date;
    private List<String> time;

    public ScheduleResponse(Integer movieId, String movieName, Integer movieLength, String description, String publishedDate, String trailerLink, String imageUrl, String backgroundImAageUrl, List<String> movieGenreNameList, List<String> imageUrlList, List<String> movieGenreDescriptions, List<String> moviePerformerNameList, List<PerformerType> moviePerformerType, List<String> movieRatingDetailNameList, List<String> movieRatingDetailDescriptions, List<String> comments, Double averageRating, String cinemaName, List<String> screenName, List<Integer> scheduleId, List<String> dayOfWeek, List<String> date, List<String> time) {
        this.movieId = movieId;
        this.movieName = movieName;
        this.movieLength = movieLength;
        this.description = description;
        this.publishedDate = publishedDate;
        this.trailerLink = trailerLink;
        this.imageUrl = imageUrl;
        this.backgroundImAageUrl = backgroundImAageUrl;
        this.movieGenreNameList = movieGenreNameList;
        this.imageUrlList = imageUrlList;
        this.movieGenreDescriptions = movieGenreDescriptions;
        this.moviePerformerNameList = moviePerformerNameList;
        this.moviePerformerType = moviePerformerType;
        this.movieRatingDetailNameList = movieRatingDetailNameList;
        this.movieRatingDetailDescriptions = movieRatingDetailDescriptions;
        this.comments = comments;
        this.averageRating = averageRating;
        this.cinemaName = cinemaName;
        this.screenName = screenName;
        this.scheduleId = scheduleId;
        this.dayOfWeek = dayOfWeek;
        this.date = date;
        this.time = time;
    }

    public Double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(Double averageRating) {
        this.averageRating = averageRating;
    }

    public String getBackgroundImAageUrl() {
        return backgroundImAageUrl;
    }

    public void setBackgroundImAageUrl(String backgroundImAageUrl) {
        this.backgroundImAageUrl = backgroundImAageUrl;
    }

    public String getCinemaName() {
        return cinemaName;
    }

    public void setCinemaName(String cinemaName) {
        this.cinemaName = cinemaName;
    }

    public List<String> getComments() {
        return comments;
    }

    public void setComments(List<String> comments) {
        this.comments = comments;
    }

    public List<String> getDate() {
        return date;
    }

    public void setDate(List<String> date) {
        this.date = date;
    }

    public List<String> getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(List<String> dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
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

    public List<Integer> getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(List<Integer> scheduleId) {
        this.scheduleId = scheduleId;
    }

    public List<String> getScreenName() {
        return screenName;
    }

    public void setScreenName(List<String> screenName) {
        this.screenName = screenName;
    }

    public List<String> getTime() {
        return time;
    }

    public void setTime(List<String> time) {
        this.time = time;
    }

    public String getTrailerLink() {
        return trailerLink;
    }

    public void setTrailerLink(String trailerLink) {
        this.trailerLink = trailerLink;
    }
}
