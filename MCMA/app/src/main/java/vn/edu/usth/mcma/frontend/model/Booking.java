package vn.edu.usth.mcma.frontend.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

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
    @Override
    public int describeContents() {
        return 0;
    }
    protected Booking(Parcel in) {

    }
    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {

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
