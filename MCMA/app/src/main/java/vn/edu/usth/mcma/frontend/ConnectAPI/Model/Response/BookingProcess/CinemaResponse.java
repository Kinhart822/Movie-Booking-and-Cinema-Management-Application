package vn.edu.usth.mcma.frontend.ConnectAPI.Model.Response.BookingProcess;

import java.util.List;

public class CinemaResponse {
    private String cityName;
    private Integer cinemaId;
    private String cinemaName;
    private String cinemaAddress;
    private List<String> screenType;
    private List<String> screenDescription;
    private List<String> foodName;
    private List<String> drinkName;
    private List<String> movieSchedules;

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Integer getCinemaId() {
        return cinemaId;
    }

    public void setCinemaId(Integer cinemaId) {
        this.cinemaId = cinemaId;
    }

    public String getCinemaName() {
        return cinemaName;
    }

    public void setCinemaName(String cinemaName) {
        this.cinemaName = cinemaName;
    }

    public String getCinemaAddress() {
        return cinemaAddress;
    }

    public void setCinemaAddress(String cinemaAddress) {
        this.cinemaAddress = cinemaAddress;
    }

    public List<String> getScreenType() {
        return screenType;
    }

    public void setScreenType(List<String> screenType) {
        this.screenType = screenType;
    }

    public List<String> getScreenDescription() {
        return screenDescription;
    }

    public void setScreenDescription(List<String> screenDescription) {
        this.screenDescription = screenDescription;
    }

    public List<String> getFoodName() {
        return foodName;
    }

    public void setFoodName(List<String> foodName) {
        this.foodName = foodName;
    }

    public List<String> getDrinkName() {
        return drinkName;
    }

    public void setDrinkName(List<String> drinkName) {
        this.drinkName = drinkName;
    }

    public List<String> getMovieSchedules() {
        return movieSchedules;
    }

    public void setMovieSchedules(List<String> movieSchedules) {
        this.movieSchedules = movieSchedules;
    }
}
