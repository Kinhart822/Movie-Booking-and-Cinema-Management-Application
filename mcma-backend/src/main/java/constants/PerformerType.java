package constants;

import lombok.Getter;

@Getter
@Deprecated
public enum PerformerType {
    DIRECTOR(0),
    ACTOR(1),;
    private final int value;
    PerformerType(final int value) {
        this.value = value;
    }
}
