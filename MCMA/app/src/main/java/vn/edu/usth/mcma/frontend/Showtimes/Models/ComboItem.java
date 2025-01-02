package vn.edu.usth.mcma.frontend.Showtimes.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class ComboItem implements Parcelable {
    private String name;
    private String imageUrl;
    private double price;
    private int quantity;

    public ComboItem(String name, String imageUrl, double price) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.price = price;
        this.quantity = 0;
    }

    // Getters and setters
    public String getName() { return name; }
    public String getImageUrl() { return imageUrl; }
    public double getPrice() { return price; }
    public int getQuantity() { return quantity; }

    public void setQuantity(int quantity) {
        this.quantity = Math.max(0, quantity);
    }

    public double getTotalPrice() {
        return price * quantity;
    }

    protected ComboItem(Parcel in) {
        name = in.readString();
        imageUrl = in.readString();
        price = in.readDouble();
        quantity = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(imageUrl);
        dest.writeDouble(price);
        dest.writeInt(quantity);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ComboItem> CREATOR = new Creator<ComboItem>() {
        @Override
        public ComboItem createFromParcel(Parcel in) {
            return new ComboItem(in);
        }

        @Override
        public ComboItem[] newArray(int size) {
            return new ComboItem[size];
        }
    };
}