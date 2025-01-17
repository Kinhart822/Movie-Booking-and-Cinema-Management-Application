package vn.edu.usth.mcma.frontend.Coupon;

public class CouponItem {
    private Integer id;
    private String name;
    private int points;
    private String dateAvailable;
    private String dateExpired;
    private String imageUrl;

    public CouponItem(Integer id, String name, int points, String dateAvailable, String dateExpired, String imageUrl) {
        this.id = id;
        this.name = name;
        this.points = points;
        this.dateAvailable = dateAvailable;
        this.dateExpired = dateExpired;
        this.imageUrl = imageUrl;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
