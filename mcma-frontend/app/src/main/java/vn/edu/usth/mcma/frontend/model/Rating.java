package vn.edu.usth.mcma.frontend.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Rating implements Parcelable {
    private Long id;
    private String name;
    private String description;

    @Override
    public int describeContents() {
        return 0;
    }
    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeLong(id);
        parcel.writeString(name);
        parcel.writeString(description);
    }
    protected Rating(Parcel in) {
        id = in.readLong();
        name = in.readString();
        description = in.readString();
    }
    public static final Parcelable.Creator<Rating> CREATOR = new Parcelable.Creator<>() {
        @Override
        public Rating createFromParcel(Parcel parcel) {
            return new Rating(parcel);
        }
        @Override
        public Rating[] newArray(int size) {
            return new Rating[size];
        }
    };
}
