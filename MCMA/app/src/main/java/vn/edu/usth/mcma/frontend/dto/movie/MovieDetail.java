package vn.edu.usth.mcma.frontend.dto.movie;

import java.util.List;

import lombok.Data;

@Data
public class MovieDetail {
    private Long id;
    private String name;
    private Integer length;
    private String overview;
    private String publishDate;
    private String trailerUrl;
    private String imageBase64;
    private String backgroundImageBase64;

    private List<String> genres;
    private List<String> directors;
    private List<String> actors;
    private String rating;
    private Double avgVotes;
}
