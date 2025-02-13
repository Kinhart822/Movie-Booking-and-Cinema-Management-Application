package vn.edu.usth.mcma.backend.dto.bookingsession;

import lombok.Data;

import java.util.List;

@Data
public class HoldSeatRequest {
    private List<HoldRootSeat> rootSeats;
    private Long timeRemaining;//todo: system elapsed time instead
}
