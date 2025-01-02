package vn.edu.usth.mcma.backend.dto;

import lombok.Builder;

import java.util.List;

@Builder
public class SeatMapResponse {
    private List<SeatPosition> seatPositions;
}
