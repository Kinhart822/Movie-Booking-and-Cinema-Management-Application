package vn.edu.usth.mcma.frontend.components.Personal;

public class View_Movie_Feedback02_Item {
    private float rating;
    private String ratingComment;

    private String movieName;
    private String content;
    private Double ratingStar;

    public View_Movie_Feedback02_Item(float rating, String ratingComment) {
        this.rating = rating;
        this.ratingComment = ratingComment;
    }

    public View_Movie_Feedback02_Item(String movieName, String content, Double ratingStar) {
        this.movieName = movieName;
        this.content = content;
        this.ratingStar = ratingStar;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Double getRatingStar() {
        return ratingStar;
    }

    public void setRatingStar(Double ratingStar) {
        this.ratingStar = ratingStar;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getRatingComment() {
        return ratingComment;
    }

    public void setRatingComment(String ratingComment) {
        this.ratingComment = ratingComment;
    }
}
