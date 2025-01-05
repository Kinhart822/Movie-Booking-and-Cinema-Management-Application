package vn.edu.usth.mcma.frontend.ConnectAPI.Model.Response.BookingProcess.Seat;

public class HeldSeatResponse {
    private  String screenName;
    private Integer seatId;
    private String heldSeat;
    private String seatStatus;
    private Integer seatColumn;
    private Integer seatRow;
    private String heldSeatsType;

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

    public String getHeldSeat() {
        return heldSeat;
    }

    public void setHeldSeat(String heldSeat) {
        this.heldSeat = heldSeat;
    }

    public String getSeatStatus() {
        return seatStatus;
    }

    public void setSeatStatus(String seatStatus) {
        this.seatStatus = seatStatus;
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

    public String getHeldSeatsType() {
        return heldSeatsType;
    }

    public void setHeldSeatsType(String heldSeatsType) {
        this.heldSeatsType = heldSeatsType;
    }
}
