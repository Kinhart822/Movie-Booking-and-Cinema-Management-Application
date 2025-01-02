package vn.edu.usth.mcma.frontend.ConnectAPI.Model.Response.BookingProcess.Seat;

import android.os.Parcel;
import android.os.Parcelable;

import vn.edu.usth.mcma.frontend.Showtimes.Models.SeatType;
import vn.edu.usth.mcma.frontend.Showtimes.Models.TicketItem;
import vn.edu.usth.mcma.frontend.Showtimes.Models.TicketType;

public class AvailableSeatResponse implements Parcelable{
    private String screenName;
    private Integer seatId;
    private String availableSeat;
    private String seatStatus;
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

    public String getSeatStatus() {
        return seatStatus;
    }

    public void setSeatStatus(String seatStatus) {
        this.seatStatus = seatStatus;
    }

    // Parcelable implementation
    protected AvailableSeatResponse(Parcel in) {
        availableSeatsType = in.readString();
        seatPrice = in.readDouble();
        seatId = in.readInt();
        availableSeat = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(availableSeatsType);
        dest.writeDouble(seatPrice);
        dest.writeInt(seatId);
        dest.writeString(availableSeat);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<AvailableSeatResponse> CREATOR = new Parcelable.Creator<AvailableSeatResponse>() {
        @Override
        public AvailableSeatResponse createFromParcel(Parcel in) {
            return new AvailableSeatResponse(in);
        }

        @Override
        public AvailableSeatResponse[] newArray(int size) {
            return new AvailableSeatResponse[size];
        }
    };
}
