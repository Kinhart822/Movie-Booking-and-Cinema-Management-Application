package vn.edu.usth.mcma.frontend.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Deprecated
public class BookingSessionRegistration {
    private String sessionId;
}
