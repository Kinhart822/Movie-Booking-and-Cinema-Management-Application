package vn.edu.usth.mcma.frontend.Showtimes.Utils;

import java.util.List;
import java.util.Locale;

import vn.edu.usth.mcma.frontend.Showtimes.Models.ComboItem;

public class PriceCalculator {
    public static String formatPrice(double price) {
        return String.format(Locale.getDefault(), "%,.0fđ", price);
    }

    public static double calculateTotalPrice(double seatPrice, List<ComboItem> comboItems) {
        double comboTotal = comboItems.stream()
                .mapToDouble(ComboItem::getTotalPrice)
                .sum();
        return seatPrice + comboTotal;
    }
}
