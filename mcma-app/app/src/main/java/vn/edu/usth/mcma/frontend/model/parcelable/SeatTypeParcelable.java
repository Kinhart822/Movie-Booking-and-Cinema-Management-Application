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
public class SeatTypeParcelable implements Parcelable, Comparable<SeatTypeParcelable> {
    private String id;
    private String description;
    private int width;
    private int height;
    private Double unitPrice;

    @Override
    public int describeContents() {
        return 0;
    }
    @Override
    public void writeToParcel(@NonNull Parcel dest, int i) {
        dest.writeString(id);
        dest.writeString(description);
        dest.writeInt(width);
        dest.writeInt(height);
        dest.writeDouble(unitPrice);
    }
    protected SeatTypeParcelable(Parcel in) {
        id = in.readString();
        description = in.readString();
        width = in.readInt();
        height = in.readInt();
        unitPrice = in.readDouble();
    }
    public static final Creator<SeatTypeParcelable> CREATOR = new Creator<>() {
        @Override
        public SeatTypeParcelable createFromParcel(Parcel parcel) {
            return new SeatTypeParcelable(parcel);
        }
        @Override
        public SeatTypeParcelable[] newArray(int size) {
            return new SeatTypeParcelable[size];
        }
    };

    @Override
    public int compareTo(SeatTypeParcelable o) {
        return Double.compare(this.getUnitPrice(), o.getUnitPrice());
    }
}
