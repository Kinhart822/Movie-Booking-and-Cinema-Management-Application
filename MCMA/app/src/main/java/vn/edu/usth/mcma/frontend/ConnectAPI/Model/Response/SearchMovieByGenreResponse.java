package vn.edu.usth.mcma.frontend.ConnectAPI.Model.Response;

import java.util.List;

public class SearchMovieByGenreResponse {
    private Integer id;
    private String name;
    private Integer length;
    private String description;
    private String imageUrl;
    private String backgroundImageUrl;
    private String trailerLink;
    private String datePublish;
    private List<String> ratingNameList;
    private List<String> ratingDescriptionList;
    private List<String> genreNameList;
    private List<String> performerNameList;
    private List<String> performerType;
    private List<String> performerSex;

    public SearchMovieByGenreResponse(String backgroundImageUrl, String datePublish, String description, List<String> genreNameList, Integer id, String imageUrl, Integer length, String name, List<String> performerNameList, List<String> performerSex, List<String> performerType, List<String> ratingDescriptionList, List<String> ratingNameList, String trailerLink) {
        this.backgroundImageUrl = backgroundImageUrl;
        this.datePublish = datePublish;
        this.description = description;
        this.genreNameList = genreNameList;
        this.id = id;
        this.imageUrl = imageUrl;
        this.length = length;
        this.name = name;
        this.performerNameList = performerNameList;
        this.performerSex = performerSex;
        this.performerType = performerType;
        this.ratingDescriptionList = ratingDescriptionList;
        this.ratingNameList = ratingNameList;
        this.trailerLink = trailerLink;
    }

    public String getBackgroundImageUrl() {
        return backgroundImageUrl;
    }

    public void setBackgroundImageUrl(String backgroundImageUrl) {
        this.backgroundImageUrl = backgroundImageUrl;
    }

    public String getDatePublish() {
        return datePublish;
    }

    public void setDatePublish(String datePublish) {
        this.datePublish = datePublish;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getGenreNameList() {
        return genreNameList;
    }

    public void setGenreNameList(List<String> genreNameList) {
        this.genreNameList = genreNameList;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getPerformerNameList() {
        return performerNameList;
    }

    public void setPerformerNameList(List<String> performerNameList) {
        this.performerNameList = performerNameList;
    }

    public List<String> getPerformerSex() {
        return performerSex;
    }

    public void setPerformerSex(List<String> performerSex) {
        this.performerSex = performerSex;
    }

    public List<String> getPerformerType() {
        return performerType;
    }

    public void setPerformerType(List<String> performerType) {
        this.performerType = performerType;
    }

    public List<String> getRatingDescriptionList() {
        return ratingDescriptionList;
    }

    public void setRatingDescriptionList(List<String> ratingDescriptionList) {
        this.ratingDescriptionList = ratingDescriptionList;
    }

    public List<String> getRatingNameList() {
        return ratingNameList;
    }

    public void setRatingNameList(List<String> ratingNameList) {
        this.ratingNameList = ratingNameList;
    }

    public String getTrailerLink() {
        return trailerLink;
    }

    public void setTrailerLink(String trailerLink) {
        this.trailerLink = trailerLink;
    }
}
