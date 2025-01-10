package vn.edu.usth.mcma.frontend.components.Personal;

public class BookingHistory_Item {
    String booking_num;
    String booking_movie;
    String booking_startdate;
    String booking_enddate;
    String booking_status;

    public BookingHistory_Item(String booking_num, String booking_movie, String booking_startdate, String booking_enddate, String booking_status) {
        this.booking_num = booking_num;
        this.booking_movie = booking_movie;
        this.booking_startdate = booking_startdate;
        this.booking_enddate = booking_enddate;
        this.booking_status = booking_status;
    }

    public String getBooking_num() {
        return booking_num;
    }

    public void setBooking_num(String booking_num) {
        this.booking_num = booking_num;
    }

    public String getBooking_movie() {
        return booking_movie;
    }

    public void setBooking_movie(String booking_movie) {
        this.booking_movie = booking_movie;
    }

    public String getBooking_startdate() {
        return booking_startdate;
    }

    public void setBooking_startdate(String booking_startdate) {
        this.booking_startdate = booking_startdate;
    }

    public String getBooking_enddate() {
        return booking_enddate;
    }

    public void setBooking_enddate(String booking_enddate) {
        this.booking_enddate = booking_enddate;
    }

    public String getBooking_status() {
        return booking_status;
    }

    public void setBooking_status(String booking_status) {
        this.booking_status = booking_status;
    }
}
