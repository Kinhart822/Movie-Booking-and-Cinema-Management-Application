package vn.edu.usth.mcma.frontend.Coupon;

public class My_Voucher_Item {
    private final String name;
    private final String point;
    private final int imageResource;

    public My_Voucher_Item(String name, String point, int imageResource) {
        this.name = name;
        this.point = point;
        this.imageResource = imageResource;
    }

    public String getName() {
        return name;
    }

    public String getPoint() {
        return point;
    }

    public int getImageResource() {
        return imageResource;
    }
}
