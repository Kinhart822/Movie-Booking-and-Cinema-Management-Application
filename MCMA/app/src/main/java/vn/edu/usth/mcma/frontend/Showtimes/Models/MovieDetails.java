package vn.edu.usth.mcma.frontend.Showtimes.Models;

import java.util.List;

public class MovieDetails {
    private String id;
    private String title;
    private int bannerImageResId; // Using int for drawable resource
    private List<String> genres;
    private int duration; // in minutes
    private String releaseDate;
    private String synopsis;
    private String director;
    private List<String> cast;
    private String classification;
    private String language;

    // Constructor
    public MovieDetails(String id, String title, int bannerImageResId, List<String> genres, int duration,
            String releaseDate, String synopsis, String director, List<String> cast, String classification,
            String language) {
        this.id = id;
        this.title = title;
        this.bannerImageResId = bannerImageResId;
        this.genres = genres;
        this.duration = duration;
        this.releaseDate = releaseDate;
        this.synopsis = synopsis;
        this.director = director;
        this.cast = cast;
        this.classification = classification;
        this.language = language;
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getBannerImageResId() {
        return bannerImageResId;
    }

    public List<String> getGenres() {
        return genres;
    }

    public int getDuration() {
        return duration;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public String getDirector() {
        return director;
    }

    public List<String> getCast() {
        return cast;
    }

    public String getClassification() {
        return classification;
    }

    public String getLanguage() {
        return language;
    }

    // Setters
    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setBannerImageResId(int bannerImageResId) {
        this.bannerImageResId = bannerImageResId;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public void setCast(List<String> cast) {
        this.cast = cast;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}