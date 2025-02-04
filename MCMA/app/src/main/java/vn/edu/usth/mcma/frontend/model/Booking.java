package vn.edu.usth.mcma.frontend.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class Booking implements Parcelable {
    //todo separate details
    private String cinemaName;
    private String screenNameDateDuration;
    private String movieName;
    private String rating;
    private String screenType;

    private Long scheduleId;
    private List<Seat> rootSeats;
    private List<AudienceType> audienceTypes;
    private Integer totalAudienceCount;
    private Double totalPrice;//todo method to get total price instead
    @Override
    public int describeContents() {
        return 0;
    }
    protected Booking(Parcel in) {
        scheduleId = in.readLong();
        cinemaName = in.readString();
        screenNameDateDuration = in.readString();
        movieName = in.readString();
        rating = in.readString();
        screenType = in.readString();
        audienceTypes = in.readParcelableList(audienceTypes = new ArrayList<>(), AudienceType.class.getClassLoader());
        totalAudienceCount = in.readInt();
        totalPrice = in.readDouble();
    }
    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeLong(scheduleId);
        dest.writeString(cinemaName);
        dest.writeString(screenNameDateDuration);
        dest.writeString(movieName);
        dest.writeString(rating);
        dest.writeString(screenType);
        dest.writeParcelableList(audienceTypes, 0);
        dest.writeInt(totalAudienceCount);
        dest.writeDouble(totalPrice);
    }
    public static final Creator<Booking> CREATOR = new Parcelable.Creator<>() {
        @Override
        public Booking createFromParcel(Parcel in) {
            return new Booking(in);
        }
        @Override
        public Booking[] newArray(int size) {
            return new Booking[size];
        }
    };
}
