package vn.edu.usth.mcma.frontend.Showtimes.Models;

public class Seat {
    private String id;
    private SeatType type;
    private boolean isAvailable;

    public Seat(String id, SeatType type, boolean isAvailable) {
        this.id = id;
        this.type = type;
        this.isAvailable = isAvailable;
    }

    // Getters and setters
    public String getId() {
        return id;
    }
    public SeatType getType() {
        return type;
    }
    public boolean isAvailable() {
        return isAvailable;
    }
    public void setAvailable(boolean available) {
        isAvailable = available;
    }
    public void setType(SeatType type) {
        this.type = type;
    }
    public void setId(String id) {
        this.id = id;
    }
}
