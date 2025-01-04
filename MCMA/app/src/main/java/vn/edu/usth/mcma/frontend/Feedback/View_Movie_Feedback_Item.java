package vn.edu.usth.mcma.frontend.Feedback;

public class View_Movie_Feedback_Item {
    private float rating;
    private String ratingComment;

    public View_Movie_Feedback_Item(float rating, String ratingComment) {
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
