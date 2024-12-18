package vn.edu.usth.mcma.frontend.ConnectAPI.Model.Response;

public class ScheduleResponse {
    private String movieName;
    private String cinemaName;
    private String screenName;
    private Integer scheduleId;
    private String dayOfWeek;
    private String date;
    private String time;

    public ScheduleResponse(String cinemaName, String date, String dayOfWeek, String movieName, Integer scheduleId, String screenName, String time) {
        this.cinemaName = cinemaName;
        this.date = date;
        this.dayOfWeek = dayOfWeek;
        this.movieName = movieName;
        this.scheduleId = scheduleId;
        this.screenName = screenName;
        this.time = time;
    }

    public String getCinemaName() {
        return cinemaName;
    }

    public void setCinemaName(String cinemaName) {
        this.cinemaName = cinemaName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public Integer getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(Integer scheduleId) {
        this.scheduleId = scheduleId;
    }

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
