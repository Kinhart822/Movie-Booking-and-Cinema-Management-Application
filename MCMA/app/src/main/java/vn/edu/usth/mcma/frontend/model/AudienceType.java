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
@AllArgsConstructor
@NoArgsConstructor
public class AudienceType implements Parcelable {
    private String id;
    private int quantity;
    private Double unitPrice;

    @Override
    public int describeContents() {
        return 0;
    }
    protected AudienceType(Parcel in) {
        id = in.readString();
        quantity = in.readInt();
        unitPrice = in.readDouble();
    }
    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeInt(quantity);
        parcel.writeDouble(unitPrice);
    }
    public static final Creator<AudienceType> CREATOR = new Parcelable.Creator<>() {
        @Override
        public AudienceType createFromParcel(Parcel in) {
            return new AudienceType(in);
        }
        @Override
        public AudienceType[] newArray(int size) {
            return new AudienceType[size];
        }
    };
}
