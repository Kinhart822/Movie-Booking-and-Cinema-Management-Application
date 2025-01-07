package vn.edu.usth.mcma.frontend.ConnectAPI.Enum;

import lombok.Getter;

@Getter
public enum SeatAvailability {
    Unavailable(0),
    Available(1),
    Held(2),;
    private final int id;
    SeatAvailability(int id) {
        this.id = id;
    }
}
