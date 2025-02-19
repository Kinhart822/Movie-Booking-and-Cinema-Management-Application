package vn.edu.usth.mcma.frontend.dto.request;

import java.util.ArrayList;
import java.util.List;

import vn.edu.usth.mcma.frontend.component.ShowtimesOld.Models.Booking.PaymentMethod;

public class BookingRequest {
    private Integer bookingId;
    private Integer movieId;
    private Integer cityId;
    private Integer cinemaId;
    private Integer screenId;
    private Integer scheduleId;
    private List<Integer> ticketIds;
    private List<Integer> seatIds;
    private List<Integer> foodIds;
    private List<Integer> drinkIds;
    private Integer movieCouponId;
    private Integer userCouponId;
    private PaymentMethod paymentMethod;

    private BookingRequest(Builder builder) {
        this.movieId = builder.movieId;
        this.cityId = builder.cityId;
        this.cinemaId = builder.cinemaId;
        this.screenId = builder.screenId;
        this.scheduleId = builder.scheduleId;
        this.ticketIds = builder.ticketIds;
        this.seatIds = builder.seatIds;
        this.foodIds = builder.foodIds;
        this.drinkIds = builder.drinkIds;
        this.movieCouponId = builder.movieCouponId;
        this.userCouponId = builder.userCouponId;
    }

    public static class Builder {
        private Integer movieId;
        private Integer cityId;
        private Integer cinemaId;
        private Integer screenId;
        private Integer scheduleId;
        private List<Integer> ticketIds;
        private List<Integer> seatIds;
        private List<Integer> foodIds = new ArrayList<>();
        private List<Integer> drinkIds = new ArrayList<>();
        private Integer movieCouponId;
        private Integer userCouponId;

        public Builder(Integer movieId, Integer cityId, Integer cinemaId, Integer screenId, Integer scheduleId, List<Integer> ticketIds, List<Integer> seatIds) {
            this.movieId = movieId;
            this.cityId = cityId;
            this.cinemaId = cinemaId;
            this.screenId = screenId;
            this.scheduleId = scheduleId;
            this.ticketIds = ticketIds;
            this.seatIds = seatIds;
        }

        public Builder setFoodIds(List<Integer> foodIds) {
            this.foodIds = foodIds;
            return this;
        }

        public Builder setDrinkIds(List<Integer> drinkIds) {
            this.drinkIds = drinkIds;
            return this;
        }

        public Builder setMovieCouponId(Integer movieCouponId) {
            this.movieCouponId = movieCouponId;
            return this;
        }

        public Builder setUserCouponId(Integer userCouponId) {
            this.userCouponId = userCouponId;
            return this;
        }

        public BookingRequest build() {
            return new BookingRequest(this);
        }
    }

    public BookingRequest(Integer bookingId, PaymentMethod paymentMethod) {
        this.bookingId = bookingId;
        this.paymentMethod = paymentMethod;
    }

    public Integer getBookingId() {
        return bookingId;
    }

    public void setBookingId(Integer bookingId) {
        this.bookingId = bookingId;
    }

    public Integer getMovieId() {
        return movieId;
    }

    public void setMovieId(Integer movieId) {
        this.movieId = movieId;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public Integer getCinemaId() {
        return cinemaId;
    }

    public void setCinemaId(Integer cinemaId) {
        this.cinemaId = cinemaId;
    }

    public Integer getScreenId() {
        return screenId;
    }

    public void setScreenId(Integer screenId) {
        this.screenId = screenId;
    }

    public Integer getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(Integer scheduleId) {
        this.scheduleId = scheduleId;
    }

    public List<Integer> getTicketIds() {
        return ticketIds;
    }

    public void setTicketIds(List<Integer> ticketIds) {
        this.ticketIds = ticketIds;
    }

    public List<Integer> getSeatIds() {
        return seatIds;
    }

    public void setSeatIds(List<Integer> seatIds) {
        this.seatIds = seatIds;
    }

    public List<Integer> getFoodIds() {
        return foodIds;
    }

    public void setFoodIds(List<Integer> foodIds) {
        this.foodIds = foodIds;
    }

    public List<Integer> getDrinkIds() {
        return drinkIds;
    }

    public void setDrinkIds(List<Integer> drinkIds) {
        this.drinkIds = drinkIds;
    }

    public Integer getMovieCouponId() {
        return movieCouponId;
    }

    public void setMovieCouponId(Integer movieCouponId) {
        this.movieCouponId = movieCouponId;
    }

    public Integer getUserCouponId() {
        return userCouponId;
    }

    public void setUserCouponId(Integer userCouponId) {
        this.userCouponId = userCouponId;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}
