package vn.edu.usth.mcma.frontend.Showtimes.Models;

import vn.edu.usth.mcma.R;

import java.text.NumberFormat;
import java.util.Locale;

public enum TicketType {
    KID("Kid", R.drawable.ic_kid, 25000),
    ADULT("Adult", R.drawable.ic_adult, 50000),
    STUDENT("Student", R.drawable.ic_student, 75000);

    private final String name;
    private final int imageResourceId;
    private final int price;

    TicketType(String name, int imageResourceId, int price) {
        this.name = name;
        this.imageResourceId = imageResourceId;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }

    public int getPrice() {
        return price;
    }

    // Phương thức định dạng giá
    public String getFormattedPrice() {
        NumberFormat format = NumberFormat.getInstance(new Locale("vi", "VN"));
        return format.format(price) + "đ";
    }
}

