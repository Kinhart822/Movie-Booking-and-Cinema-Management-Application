package vn.edu.usth.mcma.frontend.constant;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import vn.edu.usth.mcma.R;

@Getter
public enum SeatAvailability {
    Unavailable(0, R.drawable.ic_seat_unavailable),
    Available(1, R.drawable.ic_seat_empty),
    Held(2, R.drawable.ic_seat_held),;
    private final int id;
    private final Integer backgroundId;
    SeatAvailability(int id, Integer backgroundId) {
        this.id = id;
        this.backgroundId = backgroundId;
    }
    private static final Map<Integer, SeatAvailability> ID_MAP = new HashMap<>();
    static {
        for (SeatAvailability availability : SeatAvailability.values()) {
            ID_MAP.put(availability.getId(), availability);
        }
    }
    public static SeatAvailability getById(int id) {
        return ID_MAP.get(id);
    }
    public static Map<Integer, SeatAvailability> getIdMap() {
        return ID_MAP;
    }
}
