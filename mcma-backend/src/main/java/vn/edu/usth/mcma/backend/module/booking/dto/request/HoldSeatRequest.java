package vn.edu.usth.mcma.backend.module.booking.dto.request;

import java.util.List;

import lombok.Data;

@Data
public class HoldSeatRequest {
    private List<HoldRootSeat> rootSeats;
    private Long timeRemaining; // todo: system elapsed time instead
}
