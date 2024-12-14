package vn.edu.usth.mcma.frontend.ConnectAPI.Model.Response;

import java.util.List;

public class SearchMovieByGenreResponse {
    private int id;
    private String imageUrl;
    private String name;
    private Integer length;
    private String trailerLink;
    private String datePublish;
    private List<String> ratingNameList;
    private List<String> ratingDescriptionList;
    private List<String> genreNameList;
    private List<String> performerNameList;
    private List<String> performerType;
    private List<String> performerSex;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public String getTrailerLink() {
        return trailerLink;
    }

    public void setTrailerLink(String trailerLink) {
        this.trailerLink = trailerLink;
    }

    public String getDatePublish() {
        return datePublish;
    }

    public void setDatePublish(String datePublish) {
        this.datePublish = datePublish;
    }


    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public List<String> getRatingNameList() {
        return ratingNameList;
    }

    public void setRatingNameList(List<String> ratingNameList) {
        this.ratingNameList = ratingNameList;
    }

    public List<String> getGenreNameList() {
        return genreNameList;
    }

    public void setGenreNameList(List<String> genreNameList) {
        this.genreNameList = genreNameList;
    }
}
