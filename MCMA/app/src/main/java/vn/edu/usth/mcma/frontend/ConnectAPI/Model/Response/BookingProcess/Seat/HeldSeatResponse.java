package vn.edu.usth.mcma.frontend.ConnectAPI.Model.Response.BookingProcess.Seat;

public class HeldSeatResponse {
    private  String screenName;
    private Integer seatId;
    private String heldSeats;
    private Integer seatColumn;
    private Integer seatRow;
    private String heldSeatsTypeList;

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

    public String getHeldSeats() {
        return heldSeats;
    }

    public void setHeldSeats(String heldSeats) {
        this.heldSeats = heldSeats;
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

    public String getHeldSeatsTypeList() {
        return heldSeatsTypeList;
    }

    public void setHeldSeatsTypeList(String heldSeatsTypeList) {
        this.heldSeatsTypeList = heldSeatsTypeList;
    }
}
