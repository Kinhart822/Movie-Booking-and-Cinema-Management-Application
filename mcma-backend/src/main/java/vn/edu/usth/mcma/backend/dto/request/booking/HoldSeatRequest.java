package vn.edu.usth.mcma.backend.dto.request.booking;

import java.util.List;

import lombok.Data;

@Data
public class HoldSeatRequest {
    private List<HoldRootSeat> rootSeats;
    private Long timeRemaining; // todo: system elapsed time instead
}
