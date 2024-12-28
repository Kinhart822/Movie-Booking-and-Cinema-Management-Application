package vn.edu.usth.mcma.frontend.Showtimes.Models;

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

    public TicketType getType() { return type; }
    public int getQuantity() { return quantity; }

    public void setQuantity(int quantity) {
        this.quantity = Math.max(0, quantity);
    }

    // Add a method to calculate price for this ticket type
    public double getTotalPrice() { // Update to use the API-fetched price
        return price * quantity;
    }
//    public int getTotalPrice() {
//        return type.getPrice() * quantity;
//    }

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
