package vn.edu.usth.mcma.frontend.Store.Utils;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import vn.edu.usth.mcma.frontend.Store.UI.ComboItem;

public class PriceCalculator {
    public static class PriceResult {
        private final double subtotal;
        private final double surcharge;
        private final double total;

        public PriceResult(double subtotal, double surcharge) {
            this.subtotal = subtotal;
            this.surcharge = surcharge;
            this.total = subtotal + surcharge;
        }

        public double getSubtotal() { return subtotal; }
        public double getSurcharge() { return surcharge; }
        public double getTotal() { return total; }
    }

    /**
     * Calculates the total price including any applicable surcharges
     * @param items List of combo items with their quantities
     * @return PriceResult containing subtotal, surcharge, and total
     */
    public static PriceResult calculateTotalPrice(List<ComboItem> items) {
        double subtotal = items.stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum();

        // Calculate surcharge based on business rules
        // In this case, surcharge is already included in item prices
        double surcharge = 0.0;

        return new PriceResult(subtotal, surcharge);
    }

    /**
     * Formats the price in Vietnamese currency format
     * @param price The price to format
     * @return Formatted price string (e.g., "80,100đ")
     */
    public static String formatPrice(double price) {
        return String.format("%,.0fđ", price);
    }

    /**
     * Updates the total price when quantity changes
     * @param currentTotal Current total price
     * @param pricePerUnit Price per unit of the item being changed
     * @param quantityDelta Change in quantity (positive or negative)
     * @return New total price
     */
    public static double updateTotalPrice(double currentTotal, double pricePerUnit, int quantityDelta) {
        return currentTotal + (pricePerUnit * quantityDelta);
    }
}
