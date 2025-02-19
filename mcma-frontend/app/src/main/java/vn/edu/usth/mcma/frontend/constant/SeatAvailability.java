package vn.edu.usth.mcma.frontend.constant;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import vn.edu.usth.mcma.R;

@Getter
public enum SeatAvailability {
    SOLD(R.drawable.ic_seat_sold),
    BUYABLE(R.drawable.ic_seat_empty),
    HELD(R.drawable.ic_seat_held),
    ;
    private final Integer backgroundId;
    SeatAvailability(Integer backgroundId) {
        this.backgroundId = backgroundId;
    }
    private static final Map<String, SeatAvailability> ID_MAP = new HashMap<>();
    static {
        for (SeatAvailability availability : SeatAvailability.values()) {
            ID_MAP.put(availability.name(), availability);
        }
    }
    public static SeatAvailability getById(String id) {
        return ID_MAP.get(id);
    }
    public static Map<String, SeatAvailability> getIdMap() {
        return ID_MAP;
    }
}
