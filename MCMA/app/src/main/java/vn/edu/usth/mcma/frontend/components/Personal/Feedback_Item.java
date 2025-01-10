package vn.edu.usth.mcma.frontend.components.Personal;

public class Feedback_Item {
    String movie_name;
    String rating_star;
    String comment;

    public Feedback_Item(String movie_name, String rating_star, String comment) {
        this.movie_name = movie_name;
        this.rating_star = rating_star;
        this.comment = comment;
    }

    public String getMovie_name() {
        return movie_name;
    }

    public void setMovie_name(String movie_name) {
        this.movie_name = movie_name;
    }

    public String getRating_star() {
        return rating_star;
    }

    public void setRating_star(String rating_star) {
        this.rating_star = rating_star;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
