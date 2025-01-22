package vn.edu.usth.mcma.frontend.component.ShowtimesOld.Models;

import java.util.List;

import lombok.Data;

@Data
public class MovieDetails {
    private String id;
    private String title;
    private int bannerImageResId; // Using int for drawable resource
    private String movieTrailer;
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

    public MovieDetails(String id, String title, int bannerImageResId, String movieTrailer, List<String> genres, int duration, String releaseDate, String synopsis, String director, List<String> cast, String classification, String language) {
        this.id = id;
        this.title = title;
        this.bannerImageResId = bannerImageResId;
        this.movieTrailer = movieTrailer;
        this.genres = genres;
        this.duration = duration;
        this.releaseDate = releaseDate;
        this.synopsis = synopsis;
        this.director = director;
        this.cast = cast;
        this.classification = classification;
        this.language = language;
    }

}