package vn.edu.usth.mcma.frontend.component.Showtimes.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class FoodItem implements Parcelable {
    private String name;
    private String imageUrl;
    private double price;
    private int quantity;
    private int comboIds;

    public FoodItem(String name, String imageUrl, double price, int comboIds) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.price = price;
        this.quantity = 0;
        this.comboIds = comboIds;
    }

    // Getters and setters
    public int getComboIds() {
        return comboIds;
    }

    public void setComboIds(int comboIds) {
        this.comboIds = comboIds;
    }

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

    protected FoodItem(Parcel in) {
        name = in.readString();
        imageUrl = in.readString();
        price = in.readDouble();
        quantity = in.readInt();
        comboIds = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(imageUrl);
        dest.writeDouble(price);
        dest.writeInt(quantity);
        dest.writeInt(comboIds);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<FoodItem> CREATOR = new Creator<FoodItem>() {
        @Override
        public FoodItem createFromParcel(Parcel in) {
            return new FoodItem(in);
        }

        @Override
        public FoodItem[] newArray(int size) {
            return new FoodItem[size];
        }
    };
}