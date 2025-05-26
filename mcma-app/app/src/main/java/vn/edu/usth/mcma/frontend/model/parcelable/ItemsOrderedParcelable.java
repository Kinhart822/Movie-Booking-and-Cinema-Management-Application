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
public class ItemsOrderedParcelable implements Parcelable {
    private int quantity;
    private String name;
    private double totalPrice;

    @Override
    public int describeContents() {
        return 0;
    }
    protected ItemsOrderedParcelable(Parcel in) {
        quantity = in.readInt();
        name = in.readString();
        totalPrice = in.readDouble();
    }
    @Override
    public void writeToParcel(@NonNull Parcel dest, int i) {
        dest.writeInt(quantity);
        dest.writeString(name);
        dest.writeDouble(totalPrice);
    }
    public static final Creator<ItemsOrderedParcelable> CREATOR = new Creator<>() {
        @Override
        public ItemsOrderedParcelable createFromParcel(Parcel in) {
            return new ItemsOrderedParcelable(in);
        }
        @Override
        public ItemsOrderedParcelable[] newArray(int size) {
            return new ItemsOrderedParcelable[size];
        }
    };
}
