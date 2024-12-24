package vn.edu.usth.mcma.frontend.ConnectAPI.Model.Response.BookingProcess;

import java.util.List;

public class CityResponse {
    private String movieName;
    private Integer cityId;
    private String cityName;
    private List<String> cinemaNameList;

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public List<String> getCinemaNameList() {
        return cinemaNameList;
    }

    public void setCinemaNameList(List<String> cinemaNameList) {
        this.cinemaNameList = cinemaNameList;
    }
}
