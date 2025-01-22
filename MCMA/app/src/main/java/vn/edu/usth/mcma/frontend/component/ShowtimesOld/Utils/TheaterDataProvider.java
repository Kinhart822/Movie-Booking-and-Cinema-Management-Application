package vn.edu.usth.mcma.frontend.component.ShowtimesOld.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Deprecated
public class TheaterDataProvider {
    public static List<String> getCities() {
        return Arrays.asList("TPHCM", "Hà Nội", "Huế", "Đà Nẵng", "Cần Thơ", "Nha Trang", "Đà Lạt", "Vũng Tàu");
    }

    public static List<String> generateShowtimes() {
        List<String> showtimes = new ArrayList<>();
        int startHour = 10; // 10 AM
        int endHour = 22;   // 10 PM
        for (int hour = startHour; hour <= endHour; hour += 2) {
            showtimes.add(String.format("%02d:00", hour));
        }
        return showtimes;
    }
}