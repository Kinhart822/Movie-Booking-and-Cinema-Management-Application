package constants;

import lombok.Getter;

@Getter
public enum CommonStatus {
    DELETED(-3),
    INACTIVE(-1),
    ACTIVE(1),;
    private final int status;
    CommonStatus(int status) {
        this.status = status;
    }
}
