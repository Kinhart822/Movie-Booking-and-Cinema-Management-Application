package vn.edu.usth.mcma.frontend.ConnectAPI.Model.Response;

import java.util.List;

public class ScheduleSelectedByCinemaResponse {
    private String cinemaName;
    private List<Integer> scheduleId;
    private List<String> dayOfWeek;
    private List<String> date;
    private List<String> time;

    public ScheduleSelectedByCinemaResponse(String cinemaName, List<String> date, List<String> dayOfWeek, List<Integer> scheduleId, List<String> time) {
        this.cinemaName = cinemaName;
        this.date = date;
        this.dayOfWeek = dayOfWeek;
        this.scheduleId = scheduleId;
        this.time = time;
    }

    public String getCinemaName() {
        return cinemaName;
    }

    public void setCinemaName(String cinemaName) {
        this.cinemaName = cinemaName;
    }

    public List<String> getDate() {
        return date;
    }

    public void setDate(List<String> date) {
        this.date = date;
    }

    public List<String> getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(List<String> dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public List<Integer> getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(List<Integer> scheduleId) {
        this.scheduleId = scheduleId;
    }

    public List<String> getTime() {
        return time;
    }

    public void setTime(List<String> time) {
        this.time = time;
    }
}
