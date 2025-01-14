package vn.edu.usth.mcma.frontend.dto.response;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Seat implements Parcelable {
    private Integer row;
    private Integer col;
    private Integer typeId;
    private String name;
    private Integer rootRow;
    private Integer rootCol;
    private Integer availability;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Seat seat = (Seat) o;
        return Objects.equals(getRow(), seat.getRow()) && Objects.equals(getCol(), seat.getCol());
    }
    @Override
    public int hashCode() {
        return Objects.hash(getRow(), getCol());
    }
    @Override
    public int describeContents() {
        return 0;
    }
    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeInt(row);
        parcel.writeInt(col);
        parcel.writeInt(typeId);
        parcel.writeString(name);
        parcel.writeInt(rootRow);
        parcel.writeInt(rootCol);
    }
    protected Seat(Parcel in) {
        row = in.readInt();
        col = in.readInt();
        typeId = in.readInt();
        name = in.readString();
        rootRow = in.readInt();
        rootCol = in.readInt();
    }
    public static final Parcelable.Creator<Seat> CREATOR = new Parcelable.Creator<Seat>() {
        @Override
        public Seat createFromParcel(Parcel parcel) {
            return new Seat(parcel);
        }
        @Override
        public Seat[] newArray(int size) {
            return new Seat[size];
        }
    };
}
