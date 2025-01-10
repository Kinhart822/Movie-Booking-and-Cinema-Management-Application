package vn.edu.usth.mcma.frontend.components.Store.Utils;

import java.util.List;
import java.util.Locale;

import vn.edu.usth.mcma.frontend.components.Store.Models.ComboItem;

public class PriceCalculator {
    public static class PriceResult {
        private final double subtotal;
        private final double total;

        public PriceResult(double subtotal) {
            this.subtotal = subtotal;
            this.total = subtotal; // No additional charges in this case
        }

        public double getSubtotal() { return subtotal; }
        public double getTotal() { return total; }
    }

    public static PriceResult calculateTotalPrice(List<ComboItem> items) {
        double subtotal = items.stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum();
        return new PriceResult(subtotal);
    }

    public static String formatPrice(double price) {
        return String.format(Locale.getDefault(), "%,.0fđ", price);
    }
}