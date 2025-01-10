package vn.edu.usth.mcma.frontend.dto.Response.BookingProcess;

import java.util.ArrayList;
import java.util.List;

public class SendBookingResponse {
    private Integer bookingId;
    private String movieName;
    private String cityName;
    private String cinemaName;
    private String startDateTime;
    private String endDateTime;
    private String screenName;
    private List<String> ticketTypeName;
    private List<String> seatName;
    private List<String> foodNameList;
    private List<String> drinkNameList;
    private List<String> couponName;
    private Double totalPrice;
    private String bookingStatus;

    // Private constructor to prevent direct instantiation
    private SendBookingResponse(Builder builder) {
        this.bookingId = builder.bookingId;
        this.movieName = builder.movieName;
        this.cityName = builder.cityName;
        this.cinemaName = builder.cinemaName;
        this.startDateTime = builder.startDateTime;
        this.endDateTime = builder.endDateTime;
        this.screenName = builder.screenName;
        this.ticketTypeName = builder.ticketTypeName;
        this.seatName = builder.seatName;
        this.foodNameList = builder.foodNameList != null ? builder.foodNameList : new ArrayList<>();
        this.drinkNameList = builder.drinkNameList != null ? builder.drinkNameList : new ArrayList<>();
        this.couponName = builder.couponName != null ? builder.couponName : new ArrayList<>();
        this.totalPrice = builder.totalPrice;
        this.bookingStatus = builder.bookingStatus;
    }

    // Getters and Setters
    public Integer getBookingId() {
        return bookingId;
    }

    public String getMovieName() {
        return movieName;
    }

    public String getCityName() {
        return cityName;
    }

    public String getCinemaName() {
        return cinemaName;
    }

    public String getStartDateTime() {
        return startDateTime;
    }

    public String getEndDateTime() {
        return endDateTime;
    }

    public String getScreenName() {
        return screenName;
    }

    public List<String> getTicketTypeName() {
        return ticketTypeName;
    }

    public List<String> getSeatName() {
        return seatName;
    }

    public List<String> getFoodNameList() {
        return foodNameList;
    }

    public List<String> getDrinkNameList() {
        return drinkNameList;
    }

    public List<String> getCouponName() {
        return couponName;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public String getBookingStatus() {
        return bookingStatus;
    }

    // Builder class
    public static class Builder {
        private Integer bookingId;
        private String movieName;
        private String cityName;
        private String cinemaName;
        private String startDateTime;
        private String endDateTime;
        private String screenName;
        private List<String> ticketTypeName;
        private List<String> seatName;
        private List<String> foodNameList;
        private List<String> drinkNameList;
        private List<String> couponName;
        private Double totalPrice;
        private String bookingStatus;

        public Builder setBookingId(Integer bookingId) {
            this.bookingId = bookingId;
            return this;
        }

        public Builder setMovieName(String movieName) {
            this.movieName = movieName;
            return this;
        }

        public Builder setCityName(String cityName) {
            this.cityName = cityName;
            return this;
        }

        public Builder setCinemaName(String cinemaName) {
            this.cinemaName = cinemaName;
            return this;
        }

        public Builder setStartDateTime(String startDateTime) {
            this.startDateTime = startDateTime;
            return this;
        }

        public Builder setEndDateTime(String endDateTime) {
            this.endDateTime = endDateTime;
            return this;
        }

        public Builder setScreenName(String screenName) {
            this.screenName = screenName;
            return this;
        }

        public Builder setTicketTypeName(List<String> ticketTypeName) {
            this.ticketTypeName = ticketTypeName;
            return this;
        }

        public Builder setSeatName(List<String> seatName) {
            this.seatName = seatName;
            return this;
        }

        public Builder setFoodNameList(List<String> foodNameList) {
            this.foodNameList = foodNameList;
            return this;
        }

        public Builder setDrinkNameList(List<String> drinkNameList) {
            this.drinkNameList = drinkNameList;
            return this;
        }

        public Builder setCouponName(List<String> couponName) {
            this.couponName = couponName;
            return this;
        }

        public Builder setTotalPrice(Double totalPrice) {
            this.totalPrice = totalPrice;
            return this;
        }

        public Builder setBookingStatus(String bookingStatus) {
            this.bookingStatus = bookingStatus;
            return this;
        }

        public SendBookingResponse build() {
            return new SendBookingResponse(this);
        }
    }
}
