package vn.edu.usth.mcma.frontend.ConnectAPI.Model.Response;

import java.util.List;

public class BookingResponse {
    private String bookingNo;
    private Integer movieId;
    private String movieName;
    private String imageUrlMovie;
    private String cityName;
    private String cinemaName;
    private String startDateTime;
    private String endDateTime;
    private String screenName;
    private List<String> ticketTypeName;
    private List<String> seatName;
    private List<String> foodNameList;
    private List<String> drinkNameList;

    private Double totalPrice;
    private String status;

    public String getBookingNo() {
        return bookingNo;
    }

    public void setBookingNo(String bookingNo) {
        this.bookingNo = bookingNo;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getImageUrlMovie() {
        return imageUrlMovie;
    }

    public void setImageUrlMovie(String imageUrlMovie) {
        this.imageUrlMovie = imageUrlMovie;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCinemaName() {
        return cinemaName;
    }

    public void setCinemaName(String cinemaName) {
        this.cinemaName = cinemaName;
    }

    public String getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(String startDateTime) {
        this.startDateTime = startDateTime;
    }

    public String getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(String endDateTime) {
        this.endDateTime = endDateTime;
    }

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public List<String> getTicketTypeName() {
        return ticketTypeName;
    }

    public void setTicketTypeName(List<String> ticketTypeName) {
        this.ticketTypeName = ticketTypeName;
    }

    public List<String> getSeatName() {
        return seatName;
    }

    public void setSeatName(List<String> seatName) {
        this.seatName = seatName;
    }

    public List<String> getFoodNameList() {
        return foodNameList;
    }

    public void setFoodNameList(List<String> foodNameList) {
        this.foodNameList = foodNameList;
    }

    public List<String> getDrinkNameList() {
        return drinkNameList;
    }

    public void setDrinkNameList(List<String> drinkNameList) {
        this.drinkNameList = drinkNameList;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getMovieId() {
        return movieId;
    }

    public void setMovieId(Integer movieId) {
        this.movieId = movieId;
    }
}
