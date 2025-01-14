package vn.edu.usth.mcma.frontend.helper;

import java.util.Map;

import vn.edu.usth.mcma.frontend.dto.response.SeatTypeResponse;

public class SeatTypeHelper {
    private Map<Integer, SeatTypeResponse> seatTypes;
    public SeatTypeHelper(Map<Integer, SeatTypeResponse> seatTypes) {
        this.seatTypes = seatTypes;
    }

}
