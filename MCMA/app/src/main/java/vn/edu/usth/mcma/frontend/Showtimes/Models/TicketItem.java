package vn.edu.usth.mcma.frontend.Showtimes.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class TicketItem implements Parcelable {
    private TicketType type;
    private int quantity;

    public TicketItem(TicketType type) {
        this.type = type;
        this.quantity = 0;
    }

    // Getters and Setters
    public TicketType getType() { return type; }
    public int getQuantity() { return quantity; }

    public void setQuantity(int quantity) {
        this.quantity = Math.max(0, quantity);
    }

    // Add a method to calculate price for this ticket type
    public int getTotalPrice() {
        return type.getPrice() * quantity;
    }

    // Parcelable implementation (same as ComboItem)
    protected TicketItem(Parcel in) {
        type = TicketType.valueOf(in.readString());
        quantity = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(type.name());
        dest.writeInt(quantity);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<TicketItem> CREATOR = new Parcelable.Creator<TicketItem>() {
        @Override
        public TicketItem createFromParcel(Parcel in) {
            return new TicketItem(in);
        }

        @Override
        public TicketItem[] newArray(int size) {
            return new TicketItem[size];
        }
    };
}
