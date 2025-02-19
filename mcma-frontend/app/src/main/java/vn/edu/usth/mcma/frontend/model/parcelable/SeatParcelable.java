package vn.edu.usth.mcma.frontend.model.parcelable;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class SeatParcelable implements Parcelable {
    private int row;
    private int col;
    private String typeId;
    private String name;
    private int rootRow;
    private int rootCol;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SeatParcelable seat = (SeatParcelable) o;
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
    public void writeToParcel(@NonNull Parcel dest, int i) {
        dest.writeInt(row);
        dest.writeInt(col);
        dest.writeString(typeId);
        dest.writeString(name);
        dest.writeInt(rootRow);
        dest.writeInt(rootCol);
    }
    protected SeatParcelable(Parcel in) {
        row = in.readInt();
        col = in.readInt();
        typeId = in.readString();
        name = in.readString();
        rootRow = in.readInt();
        rootCol = in.readInt();
    }
    public static final Parcelable.Creator<SeatParcelable> CREATOR = new Parcelable.Creator<>() {
        @Override
        public SeatParcelable createFromParcel(Parcel parcel) {
            return new SeatParcelable(parcel);
        }
        @Override
        public SeatParcelable[] newArray(int size) {
            return new SeatParcelable[size];
        }
    };
}
