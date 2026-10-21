package vn.edu.usth.mcma.backend.common.enums;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;

@Getter
public enum PerformerType {
    Director(0),
    Actor(1),
    ;
    private final int id;

    PerformerType(int id) {
        this.id = id;
    }

    private static final Map<Integer, PerformerType> ID_MAP = new HashMap<>();

    static {
        for (PerformerType performerType : PerformerType.values()) {
            ID_MAP.put(performerType.getId(), performerType);
        }
    }

    public static PerformerType getById(int id) {
        return ID_MAP.get(id);
    }
}
