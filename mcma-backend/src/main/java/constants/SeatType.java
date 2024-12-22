package constants;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public enum SeatType {
    UNAVAILABLE(-1, "Unavailable space",1,1),
    AVAILABLE(0, "Available space",1,1),
    NORMAL(1, "For normies",1,1),
    VIP(2, "For VIP",1,1),
    LOVERS(3, "For lovers",2,1),
    BED(4, "For lovers with back pain",2,3),;
    private final int id;
    private final String description;
    private final int width;
    private final int length;
    SeatType(int id, String description, int width, int length) {
        this.id = id;
        this.description = description;
        this.width = width;
        this.length = length;
    }
    private static final Map<Integer, SeatType> ID_MAP = new HashMap<>();
    static {
        for (SeatType seatType : SeatType.values()) {
            ID_MAP.put(seatType.getId(), seatType);
        }
    }
    public static SeatType getById(int id) {
        return ID_MAP.get(id);
    }
    public static Map<Integer, SeatType> getIdMap() {
        return ID_MAP;
    }
}
