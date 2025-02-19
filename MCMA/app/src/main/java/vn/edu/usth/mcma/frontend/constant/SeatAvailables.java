package vn.edu.usth.mcma.frontend.constant;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import vn.edu.usth.mcma.R;

@Getter
public enum SeatAvailables {
    SELECTED(-2, R.drawable.ic_seat_selecting),
    STANDARD(1, R.drawable.ic_seat_standard),
    VIP(2, R.drawable.ic_seat_vip),
    LOVERS(3, R.drawable.ic_seat_lovers),
    BED(4, R.drawable.ic_seat_bed),;
    private final Integer id;
    private final int backgroundId;
    SeatAvailables(int id, int backgroundId) {
        this.id = id;
        this.backgroundId = backgroundId;
    }
    private static final Map<String, SeatAvailables> ID_MAP = new HashMap<>();
    static {
        for (SeatAvailables seatAvailables : SeatAvailables.values()) {
            ID_MAP.put(seatAvailables.name(), seatAvailables);
        }
    }
    public static SeatAvailables getById(String id) {
        return ID_MAP.get(id);
    }
    public static Map<String, SeatAvailables> getIdMap() {
        return ID_MAP;
    }
}
