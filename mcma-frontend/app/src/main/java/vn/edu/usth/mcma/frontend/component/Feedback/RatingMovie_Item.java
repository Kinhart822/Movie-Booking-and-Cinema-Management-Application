package vn.edu.usth.mcma.frontend.component.Feedback;

public class RatingMovie_Item {
    private String movie_name;
    private String movie_type;
    private int movie_image;

    public RatingMovie_Item(String movie_name, String movie_type, int movie_image) {
        this.movie_name = movie_name;
        this.movie_type = movie_type;
        this.movie_image = movie_image;
    }

    public String getMovie_name() {
        return movie_name;
    }

    public void setMovie_name(String movie_name) {
        this.movie_name = movie_name;
    }

    public String getMovie_type() {
        return movie_type;
    }

    public void setMovie_type(String movie_type) {
        this.movie_type = movie_type;
    }

    public int getMovie_image() {
        return movie_image;
    }

    public void setMovie_image(int movie_image) {
        this.movie_image = movie_image;
    }
}
