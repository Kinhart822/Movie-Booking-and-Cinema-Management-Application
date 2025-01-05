package vn.edu.usth.mcma.frontend.ConnectAPI.Model.Response.BookingProcess;

public class ScreenResponse {
    private String cinemaName;
    private Integer screenId;
    private String screenName;
    private String screenType;
    private String screenDescription;

    public String getCinemaName() {
        return cinemaName;
    }

    public void setCinemaName(String cinemaName) {
        this.cinemaName = cinemaName;
    }

    public Integer getScreenId() {
        return screenId;
    }

    public void setScreenId(Integer screenId) {
        this.screenId = screenId;
    }

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public String getScreenType() {
        return screenType;
    }

    public void setScreenType(String screenType) {
        this.screenType = screenType;
    }

    public String getScreenDescription() {
        return screenDescription;
    }

    public void setScreenDescription(String screenDescription) {
        this.screenDescription = screenDescription;
    }
}
