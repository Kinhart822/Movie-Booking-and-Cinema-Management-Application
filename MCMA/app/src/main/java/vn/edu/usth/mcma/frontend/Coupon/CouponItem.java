package vn.edu.usth.mcma.frontend.Coupon;

public class CouponItem {
    private String name;
    private int points;
    private String imageUrl;

    public CouponItem(String name, int points, String imageUrl) {
        this.name = name;
        this.points = points;
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public int getPoints() {
        return points;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
