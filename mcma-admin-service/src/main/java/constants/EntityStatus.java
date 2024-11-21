package constants;

import lombok.Getter;

@Getter
public enum EntityStatus {
    DELETED(-1),
    CREATED(1),;
    private final int status;
    EntityStatus(int status) {
        this.status = status;
    }
}
