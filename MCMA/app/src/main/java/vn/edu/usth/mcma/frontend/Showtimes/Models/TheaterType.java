package vn.edu.usth.mcma.frontend.Showtimes.Models;

public enum TheaterType {
    REGULAR("1 loại"),
    FIRST_CLASS("First class");

    private final String displayName;

    TheaterType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
