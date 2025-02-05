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
import vn.edu.usth.mcma.frontend.model.parcelable.ConcessionParcelable;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class Booking implements Parcelable {
    //todo consider separate details
    private Long movieId;
    private String cinemaName;
    private String screenNameDateDuration;
    private String movieName;
    private String rating;
    private String screenType;

    private String sessionId;
    private Long limitPlusCurrentElapsedBootTime;
    private Long scheduleId;
    private List<Seat> rootSeats;
    private List<AudienceType> audienceTypes;//todo get quantity>0
    private List<ConcessionParcelable> concessions;
    private Integer totalAudience;// todo method to get total audience instead
    private Double totalPrice;//todo method to get total price instead
    @Override
    public int describeContents() {
        return 0;
    }
    protected Booking(Parcel in) {
        movieId = in.readLong();
        cinemaName = in.readString();
        screenNameDateDuration = in.readString();
        movieName = in.readString();
        rating = in.readString();
        screenType = in.readString();

        sessionId = in.readString();
        limitPlusCurrentElapsedBootTime = in.readLong();
        scheduleId = in.readLong();
        rootSeats = in.readParcelableList(rootSeats = new ArrayList<>(), Seat.class.getClassLoader());
        audienceTypes = in.readParcelableList(audienceTypes = new ArrayList<>(), AudienceType.class.getClassLoader());
        concessions = in.readParcelableList(concessions = new ArrayList<>(), ConcessionParcelable.class.getClassLoader());
        totalAudience = in.readInt();
        totalPrice = in.readDouble();
    }
    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeLong(movieId);
        dest.writeString(cinemaName);
        dest.writeString(screenNameDateDuration);
        dest.writeString(movieName);
        dest.writeString(rating);
        dest.writeString(screenType);

        dest.writeString(sessionId);
        dest.writeLong(limitPlusCurrentElapsedBootTime);
        dest.writeLong(scheduleId);
        dest.writeParcelableList(rootSeats, 0);
        dest.writeParcelableList(audienceTypes, 0);
        dest.writeParcelableList(concessions, 0);
        dest.writeInt(totalAudience);
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
