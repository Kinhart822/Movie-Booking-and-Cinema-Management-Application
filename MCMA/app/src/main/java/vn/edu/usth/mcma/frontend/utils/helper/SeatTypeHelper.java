package vn.edu.usth.mcma.frontend.utils.helper;

import java.util.Map;

import vn.edu.usth.mcma.frontend.model.response.SeatTypeResponse;

public class SeatTypeHelper {
    private Map<Integer, SeatTypeResponse> seatTypes;
    public SeatTypeHelper(Map<Integer, SeatTypeResponse> seatTypes) {
        this.seatTypes = seatTypes;
    }

}
