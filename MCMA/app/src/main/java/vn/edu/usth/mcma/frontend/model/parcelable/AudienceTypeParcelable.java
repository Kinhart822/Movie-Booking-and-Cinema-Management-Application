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
public class AudienceTypeParcelable implements Parcelable {
    private String id;
    private double unitPrice;
    private int quantity;

    @Override
    public int describeContents() {
        return 0;
    }
    protected AudienceTypeParcelable(Parcel in) {
        id = in.readString();
        quantity = in.readInt();
        unitPrice = in.readDouble();
    }
    @Override
    public void writeToParcel(@NonNull Parcel dest, int i) {
        dest.writeString(id);
        dest.writeInt(quantity);
        dest.writeDouble(unitPrice);
    }
    public static final Creator<AudienceTypeParcelable> CREATOR = new Parcelable.Creator<>() {
        @Override
        public AudienceTypeParcelable createFromParcel(Parcel in) {
            return new AudienceTypeParcelable(in);
        }
        @Override
        public AudienceTypeParcelable[] newArray(int size) {
            return new AudienceTypeParcelable[size];
        }
    };
}
