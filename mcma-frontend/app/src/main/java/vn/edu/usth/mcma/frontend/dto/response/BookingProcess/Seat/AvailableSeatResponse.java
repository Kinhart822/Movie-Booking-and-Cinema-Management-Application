package vn.edu.usth.mcma.frontend.dto.response.BookingProcess.Seat;

import android.os.Parcel;
import android.os.Parcelable;

import lombok.Data;

@Data
public class AvailableSeatResponse implements Parcelable{
    private Integer seatId;
    private String availableSeat;
    private String seatStatus;
    private Integer seatColumn;
    private Integer seatRow;
    private String availableSeatsType;
    private Double seatPrice;
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
