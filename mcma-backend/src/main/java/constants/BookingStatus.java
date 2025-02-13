package constants;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public enum BookingStatus {
    WAITING_FOR_SEAT,
    IN_PROGRESS,
    PENDING_PAYMENT,
    CONFIRMED,
}
