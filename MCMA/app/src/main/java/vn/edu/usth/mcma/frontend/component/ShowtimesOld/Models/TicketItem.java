package vn.edu.usth.mcma.frontend.component.ShowtimesOld.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class TicketItem implements Parcelable {

    private TicketType type;
    private int quantity;
    private double price;
    private int ticketIds;

    public TicketItem(TicketType type, double price, int ticketIds) {
        this.type = type;
        this.price = price;
        this.ticketIds = ticketIds;
        this.quantity = 0;
    }

    // Getters and Setters
    public int getTicketIds() {
        return ticketIds;
    }

    public void setTicketIds(int ticketIds) {
        this.ticketIds = ticketIds;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public TicketType getType() {
        return type;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = Math.max(0, quantity);
    }

    public double getTotalPrice() {
        return price * quantity;
    }

    // Parcelable implementation
    protected TicketItem(Parcel in) {
        type = TicketType.valueOf(in.readString()); // Read type
        quantity = in.readInt();                  // Read quantity
        price = in.readDouble();                  // Read price
        ticketIds = in.readInt();                 // Read ticketIds
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(type.name());            // Write type
        dest.writeInt(quantity);                  // Write quantity
        dest.writeDouble(price);                  // Write price
        dest.writeInt(ticketIds);                 // Write ticketIds
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
