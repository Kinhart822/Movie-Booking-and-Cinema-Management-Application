package vn.edu.usth.mcma.frontend.Showtimes.Models;

public class Coupon {
    private String id;
    private String name;
    private double discountPercentage;
    private boolean isSelected;

    public Coupon(String id, String name) {
        this.id = id;
        this.name = name;
        // Extract discount percentage from name
        this.discountPercentage = extractDiscountPercentage(name);
        this.isSelected = false;
    }

    private double extractDiscountPercentage(String name) {
        if (name.equals("Not use coupon")) return 0;
        // Remove '%' sign and parse the number
        try {
            return Double.parseDouble(name.replace("%", "")) / 100.0;
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    // Existing getters and setters
    public String getId() { return id; }
    public String getName() { return name; }
    public double getDiscountPercentage() { return discountPercentage; }
    public boolean isSelected() { return isSelected; }
    public void setSelected(boolean selected) { isSelected = selected; }
}
