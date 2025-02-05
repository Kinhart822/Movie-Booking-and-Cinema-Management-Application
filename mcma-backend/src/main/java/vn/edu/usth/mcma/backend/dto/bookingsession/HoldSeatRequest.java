package vn.edu.usth.mcma.backend.dto.bookingsession;

import lombok.Data;

import java.util.List;

@Data
public class HoldSeatRequest {
    private String sessionId;
    private List<HoldRootSeat> rootSeats;
    private Long timeRemaining;
}
