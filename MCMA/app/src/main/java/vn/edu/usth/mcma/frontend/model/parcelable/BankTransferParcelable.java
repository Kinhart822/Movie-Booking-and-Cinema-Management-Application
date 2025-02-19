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
@NoArgsConstructor
@AllArgsConstructor
public class BankTransferParcelable implements Parcelable {
    private String bankId;
    private Double price;
    private String transactionContent;

    @Override
    public int describeContents() {
        return 0;
    }
    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(bankId);
        dest.writeDouble(price);
        dest.writeString(transactionContent);
    }
    protected BankTransferParcelable(Parcel source) {
        bankId = source.readString();
        price = source.readDouble();
        transactionContent = source.readString();
    }
    public static final Creator<BankTransferParcelable> CREATOR = new Creator<>() {
        @Override
        public BankTransferParcelable createFromParcel(Parcel source) {
            return new BankTransferParcelable(source);
        }
        @Override
        public BankTransferParcelable[] newArray(int size) {
            return new BankTransferParcelable[0];
        }
    };
}
