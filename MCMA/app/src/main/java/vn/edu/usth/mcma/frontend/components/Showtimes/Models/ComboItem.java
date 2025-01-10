package vn.edu.usth.mcma.frontend.components.Showtimes.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class ComboItem implements Parcelable {
    private String name;
    private String imageUrl;
    private double price;
    private int quantity;
    private int comboIds;
    private int type;  // 0 = FoodItem, 1 = DrinkItem

//    public ComboItem(String name, String imageUrl, double price, int comboIds) {
//        this.name = name;
//        this.imageUrl = imageUrl;
//        this.price = price;
//        this.quantity = 0;
//        this.comboIds = comboIds;
//    }

    public ComboItem(String name, String imageUrl, double price, int comboIds, int type) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.price = price;
        this.quantity = 0;
        this.comboIds = comboIds;
        this.type = type;
    }

    // Getters and setters
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

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

    protected ComboItem(Parcel in) {
        name = in.readString();
        imageUrl = in.readString();
        price = in.readDouble();
        quantity = in.readInt();
        comboIds = in.readInt();
        type = in.readInt(); // Read the type (0 or 1)
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(imageUrl);
        dest.writeDouble(price);
        dest.writeInt(quantity);
        dest.writeInt(comboIds);
        dest.writeInt(type); // Write the type (0 or 1)
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