package vn.edu.usth.mcma.frontend.Showtimes.Models;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Movie {
    private String id;
    private String title;
    private List<String> showtimes;
    private Map<TheaterType, List<String>> showtimesByType;

    public Movie(String id, String title, Map<TheaterType, List<String>> showtimesByType) {
        this.id = id;
        this.title = title;
        this.showtimesByType = showtimesByType;
    }

    public List<String> getShowtimesForType(TheaterType type) {
        return showtimesByType.getOrDefault(type, new ArrayList<>());
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Map<TheaterType, List<String>> getShowtimesByType() {
        return showtimesByType;
    }

    public void setShowtimesByType(Map<TheaterType, List<String>> showtimesByType) {
        this.showtimesByType = showtimesByType;
    }

    public List<String> getShowtimes() {
        return showtimes;
    }

    public void setShowtimes(List<String> showtimes) {
        this.showtimes = showtimes;
    }
}
