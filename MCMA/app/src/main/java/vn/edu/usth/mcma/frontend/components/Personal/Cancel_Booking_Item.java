package vn.edu.usth.mcma.frontend.components.Personal;

public class Cancel_Booking_Item {
    private int bookingId;
    private String movie_name;
    private String movie_type;
    private int movie_image;
    private String movieImageUrl;
    private String startDateTime;

    public Cancel_Booking_Item(String movie_name, String movie_type, int movie_image) {
        this.movie_name = movie_name;
        this.movie_type = movie_type;
        this.movie_image = movie_image;
    }

    public Cancel_Booking_Item(String movie_name, String movie_type, String movieImageUrl) {
        this.movie_name = movie_name;
        this.movie_type = movie_type;
        this.movieImageUrl = movieImageUrl;
    }

    public Cancel_Booking_Item(int bookingId, String movie_name, String movie_type, String movieImageUrl) {
        this.bookingId = bookingId;
        this.movie_name = movie_name;
        this.movie_type = movie_type;
        this.movieImageUrl = movieImageUrl;
    }

    public Cancel_Booking_Item(int bookingId, String movie_name, String movie_type, String movieImageUrl, String startDateTime) {
        this.bookingId = bookingId;
        this.movie_name = movie_name;
        this.movie_type = movie_type;
        this.movieImageUrl = movieImageUrl;
        this.startDateTime = startDateTime;
    }

    public String getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(String startDateTime) {
        this.startDateTime = startDateTime;
    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public String getMovieImageUrl() {
        return movieImageUrl;
    }

    public void setMovieImageUrl(String movieImageUrl) {
        this.movieImageUrl = movieImageUrl;
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
