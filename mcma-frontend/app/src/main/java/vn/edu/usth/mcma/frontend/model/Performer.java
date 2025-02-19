package vn.edu.usth.mcma.frontend.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Performer implements Parcelable {
    private Long id;
    private String name;
    private Integer typeId;
    private Instant dob;
    private Integer sex;

    @Override
    public int describeContents() {
        return 0;
    }
    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeLong(id);
        parcel.writeString(name);
        parcel.writeInt(typeId);
        parcel.writeSerializable(dob);
        parcel.writeInt(sex);
    }
    protected Performer(Parcel in) {
        id = in.readLong();
        name = in.readString();
        typeId = in.readInt();
        dob = (Instant) in.readSerializable();
        sex = in.readInt();
    }
    public static final Parcelable.Creator<Performer> CREATOR = new Parcelable.Creator<>() {
        @Override
        public Performer createFromParcel(Parcel parcel) {
            return new Performer(parcel);
        }
        @Override
        public Performer[] newArray(int size) {
            return new Performer[size];
        }
    };
}
