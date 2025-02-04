package vn.edu.usth.mcma.frontend.model.parcelable;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ConcessionParcelable implements Parcelable {
    private Long id;
    private Double comboPrice;
    private int quantity;

    @Override
    public int describeContents() {
        return 0;
    }
    protected ConcessionParcelable(Parcel in) {
        id = in.readLong();
        comboPrice = in.readDouble();
        quantity = in.readInt();
    }
    @Override
    public void writeToParcel(@NonNull Parcel dest, int i) {
        dest.writeLong(id);
        dest.writeDouble(comboPrice);
        dest.writeInt(quantity);
    }
    public static final Creator<ConcessionParcelable> CREATOR = new Creator<>() {
        @Override
        public ConcessionParcelable createFromParcel(Parcel in) {
            return new ConcessionParcelable(in);
        }
        @Override
        public ConcessionParcelable[] newArray(int size) {
            return new ConcessionParcelable[size];
        }
    };
}
