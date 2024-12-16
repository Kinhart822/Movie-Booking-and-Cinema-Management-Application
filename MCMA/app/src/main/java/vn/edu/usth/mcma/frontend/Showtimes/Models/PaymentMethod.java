package vn.edu.usth.mcma.frontend.Showtimes.Models;

public class PaymentMethod {
    private String name;
    private int iconResource;
    public PaymentMethod(String name, int iconResource) {
        this.name = name;
        this.iconResource = iconResource;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getIconResource() {
        return iconResource;
    }
    public void setIconResource(int iconResource) {
        this.iconResource = iconResource;
    }
}