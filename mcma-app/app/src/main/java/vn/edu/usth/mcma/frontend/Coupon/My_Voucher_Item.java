package vn.edu.usth.mcma.frontend.Coupon;

public class My_Voucher_Item {
    private Integer id;
    private String name;
    private String point;
    private String dateAvailable;
    private String dateExpired;
    private int imageResource;
    private String imageUrl;

    public My_Voucher_Item(String name, String point, int imageResource) {
        this.name = name;
        this.point = point;
        this.imageResource = imageResource;
    }

    public My_Voucher_Item(Integer id, String name, String point, String dateAvailable, String dateExpired, String imageUrl) {
        this.id = id;
        this.name = name;
        this.point = point;
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

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    public int getImageResource() {
        return imageResource;
    }

    public void setImageResource(int imageResource) {
        this.imageResource = imageResource;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
