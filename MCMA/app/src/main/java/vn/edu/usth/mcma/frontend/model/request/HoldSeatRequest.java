package vn.edu.usth.mcma.frontend.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HoldSeatRequest {
    private List<HoldRootSeat> rootSeats;
    private Long timeRemaining;
}
