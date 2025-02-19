package constants;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public enum Sex {
    Female(0),
    Male(1),;
    private final int id;
    Sex(int id) {
        this.id = id;
    }
    private static final Map<Integer, Sex> ID_MAP = new HashMap<>();
    static {
        for (Sex sex : Sex.values()) {
            ID_MAP.put(sex.getId(), sex);
        }
    }
    public static Sex getById(int id) {
        return ID_MAP.get(id);
    }
}
