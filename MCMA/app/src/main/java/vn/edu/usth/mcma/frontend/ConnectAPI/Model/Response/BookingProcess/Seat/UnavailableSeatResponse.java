package vn.edu.usth.mcma.frontend.ConnectAPI.Model.Response.BookingProcess.Seat;

public class UnavailableSeatResponse {
    private  String screenName;
    private Integer seatId;
    private String unAvailableSeat;
    private String seatStatus;
    private Integer seatColumn;
    private Integer seatRow;
    private String unAvailableSeatsType;

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public Integer getSeatId() {
        return seatId;
    }

    public void setSeatId(Integer seatId) {
        this.seatId = seatId;
    }

    public String getUnAvailableSeat() {
        return unAvailableSeat;
    }

    public void setUnAvailableSeat(String unAvailableSeat) {
        this.unAvailableSeat = unAvailableSeat;
    }

    public Integer getSeatColumn() {
        return seatColumn;
    }

    public void setSeatColumn(Integer seatColumn) {
        this.seatColumn = seatColumn;
    }

    public Integer getSeatRow() {
        return seatRow;
    }

    public void setSeatRow(Integer seatRow) {
        this.seatRow = seatRow;
    }

    public String getUnAvailableSeatsType() {
        return unAvailableSeatsType;
    }

    public void setUnAvailableSeatsType(String unAvailableSeatsType) {
        this.unAvailableSeatsType = unAvailableSeatsType;
    }

    public String getSeatStatus() {
        return seatStatus;
    }

    public void setSeatStatus(String seatStatus) {
        this.seatStatus = seatStatus;
    }
}
