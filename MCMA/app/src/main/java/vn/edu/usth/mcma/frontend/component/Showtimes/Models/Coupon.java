package vn.edu.usth.mcma.frontend.component.Showtimes.Models;

public class Coupon {
    private int id;
    private String name;
    private double discountPercentage;
    private int type; // 1 là movieCoupon, 0 là userCoupon
    private boolean isSelected;

    public Coupon(String name, int type, int id) {
        this.name = name;
        this.type = type;  // 1 = MovieCoupon, 0 = UserCoupon
        this.id = id;
        this.discountPercentage = extractDiscountPercentage(name);
        this.isSelected = false;
    }

    private double extractDiscountPercentage(String name) {
        if (name == null || name.isEmpty()) return 0;
        // Tìm giá trị phần trăm ở cuối chuỗi
        try {
            String[] parts = name.split("-");
            String discountPart = parts[parts.length - 1].replace("%", "").trim();
            return Double.parseDouble(discountPart) / 100.0;
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            // Trả về 0 nếu không tìm thấy hoặc chuỗi không hợp lệ
            return 0;
        }
    }

    // Existing getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(double discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
