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
    private String screenName;
    private Integer scheduleId;
    private String dayOfWeek;
    private String date;
    private String time;

    public ScheduleResponse(String cinemaName, String date, String dayOfWeek, Integer movieId, String movieName, Integer scheduleId, String screenName, String time) {
        this.cinemaName = cinemaName;
        this.date = date;
        this.dayOfWeek = dayOfWeek;
        this.movieId = movieId;
        this.movieName = movieName;
        this.scheduleId = scheduleId;
        this.screenName = screenName;
        this.time = time;
    }



    public String getBackgroundImAageUrl() {
        return backgroundImAageUrl;
    }

    public void setBackgroundImAageUrl(String backgroundImAageUrl) {
        this.backgroundImAageUrl = backgroundImAageUrl;
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

    public Integer getMovieLength() {
        return movieLength;
    }

    public void setMovieLength(Integer movieLength) {
        this.movieLength = movieLength;
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

    public String getTrailerLink() {
        return trailerLink;
    }

    public void setTrailerLink(String trailerLink) {
        this.trailerLink = trailerLink;
    }

    public Integer getMovieId() {
        return movieId;
    }

    public void setMovieId(Integer movieId) {
        this.movieId = movieId;
    }

    public String getCinemaName() {
        return cinemaName;
    }

    public void setCinemaName(String cinemaName) {
        this.cinemaName = cinemaName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public Integer getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(Integer scheduleId) {
        this.scheduleId = scheduleId;
    }

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
