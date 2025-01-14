package vn.edu.usth.mcma.frontend.helper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import lombok.Getter;
import vn.edu.usth.mcma.frontend.dto.response.Seat;

public class SeatMapHelper {
    private final List<Seat> seats;
    @Getter
    private Map<Integer, Map<Integer, Seat>> seatMatrix;
    @Getter
    private Map<Integer, Map<Integer, List<Seat>>> rootSeatMatrix;
    public SeatMapHelper(List<Seat> seats) {
        this.seats = seats;
        this.generateSeatMatrix();
    }
    private void generateSeatMatrix() {
        seatMatrix = new TreeMap<>();
        rootSeatMatrix = new TreeMap<>();
        for (Seat seat : seats) {
            seatMatrix
                    .computeIfAbsent(seat.getRow(), r -> new TreeMap<>())
                    .put(seat.getCol(), seat);
            rootSeatMatrix
                    .computeIfAbsent(seat.getRootRow(), r -> new TreeMap<>())
                    .computeIfAbsent(seat.getRootCol(), c -> new ArrayList<>())
                    .add(seat);
        }
    }
}
