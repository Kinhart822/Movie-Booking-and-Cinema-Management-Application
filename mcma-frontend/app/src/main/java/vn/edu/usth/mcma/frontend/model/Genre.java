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
public class Genre implements Parcelable {
    private Long id;
    private String name;
    private String description;
    private String imageBase64;

    @Override
    public int describeContents() {
        return 0;
    }
    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeLong(id);
        parcel.writeString(name);
        parcel.writeString(description);
        parcel.writeString(imageBase64);
    }
    protected Genre(Parcel in) {
        id = in.readLong();
        name = in.readString();
        description = in.readString();
        imageBase64 = in.readString();
    }
    public static final Parcelable.Creator<Genre> CREATOR = new Parcelable.Creator<>() {
        @Override
        public Genre createFromParcel(Parcel parcel) {
            return new Genre(parcel);
        }
        @Override
        public Genre[] newArray(int size) {
            return new Genre[size];
        }
    };
}
