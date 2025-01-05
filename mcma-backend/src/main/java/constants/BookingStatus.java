package constants;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public enum BookingStatus {
    CONFIRMED,
    IN_PROCESS,
    PENDING_PAYMENT,
    COMPLETED,
    CANCELLED,
}
