package vn.edu.usth.mcma.frontend.ConnectAPI.Model.Response.BookingProcess.Seat;

public class AvailableSeatResponse {
    private String screenName;
    private Integer seatId;
    private String availableSeat;
    private Integer seatColumn;
    private Integer seatRow;
    private String availableSeatsType;
    private Double seatPrice;

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

    public String getAvailableSeat() {
        return availableSeat;
    }

    public void setAvailableSeat(String availableSeat) {
        this.availableSeat = availableSeat;
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

    public String getAvailableSeatsType() {
        return availableSeatsType;
    }

    public void setAvailableSeatsType(String availableSeatsType) {
        this.availableSeatsType = availableSeatsType;
    }

    public Double getSeatPrice() {
        return seatPrice;
    }

    public void setSeatPrice(Double seatPrice) {
        this.seatPrice = seatPrice;
    }
}
