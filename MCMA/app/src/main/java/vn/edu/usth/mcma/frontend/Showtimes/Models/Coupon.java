package vn.edu.usth.mcma.frontend.Showtimes.Models;

public class Coupon {
    private String name;
    private double discountPercentage;
    private boolean isSelected;

    public Coupon(String name) {
        this.name = name;
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
