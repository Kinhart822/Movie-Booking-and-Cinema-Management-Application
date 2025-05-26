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
public class Review implements Parcelable {
    private Long id;
    private Long userId;
    private String userComment;
    private Integer userVote;

    @Override
    public int describeContents() {
        return 0;
    }
    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeLong(id);
        parcel.writeLong(userId);
        parcel.writeString(userComment);
        parcel.writeInt(userVote);
    }
    protected Review(Parcel in) {
        id = in.readLong();
        userId = in.readLong();
        userComment = in.readString();
        userVote = in.readInt();
    }
    public static final Parcelable.Creator<Review> CREATOR = new Parcelable.Creator<>() {
        @Override
        public Review createFromParcel(Parcel parcel) {
            return new Review(parcel);
        }
        @Override
        public Review[] newArray(int size) {
            return new Review[size];
        }
    };
}
