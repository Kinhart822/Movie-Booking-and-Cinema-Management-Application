package vn.edu.usth.mcma.frontend.components.Showtimes.Models.Booking;

import vn.edu.usth.mcma.R;

public enum PaymentMethod {
    Cash("Cash Payment", R.drawable.money ),
    Bank_Transfer("Bank Transfer", R.drawable.bank);

    private final String displayName;
    private final int iconResource;

    PaymentMethod(String displayName, int iconResource) {
        this.displayName = displayName;
        this.iconResource = iconResource;
    }

    public String getDisplayName() {
        return displayName;
    }

    public int getIconResource() {
        return iconResource;
    }
}

