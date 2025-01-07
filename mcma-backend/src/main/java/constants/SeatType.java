package constants;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public enum SeatType {
    NOT_PLACEABLE(-1, "Not a place to sit",1,1,null),
    PLACEABLE(0, "You can sit here",1,1,null),
    NORMAL(1, "For normies",1,1,30.0),
    VIP(2, "For VIP",1,1, 40.0),
    LOVERS(3, "For lovers",2,1,50.0),
    BED(4, "For lovers with back pain",2,3,120.0),;
    private final int id;
    private final String description;
    private final int width;
    private final int length;
    private final Double price;
    SeatType(int id, String description, int width, int length, Double price) {
        this.id = id;
        this.description = description;
        this.width = width;
        this.length = length;
        this.price = price;
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
