package vn.edu.usth.mcma.frontend.Personal;

public class View_Movie_Feedback02_Item {
    private float rating;
    private String ratingComment;

    public View_Movie_Feedback02_Item(float rating, String ratingComment) {
        this.rating = rating;
        this.ratingComment = ratingComment;
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
