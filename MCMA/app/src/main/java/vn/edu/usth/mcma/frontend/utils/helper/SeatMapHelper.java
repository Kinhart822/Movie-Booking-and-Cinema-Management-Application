package vn.edu.usth.mcma.frontend.utils.helper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import lombok.Getter;
import vn.edu.usth.mcma.frontend.model.Seat;

public class SeatMapHelper {
    private final List<Seat> seatResponses;
    @Getter
    private Map<Integer, Map<Integer, Seat>> seatMatrix;
    @Getter
    private Map<Integer, Map<Integer, List<Seat>>> rootSeatMatrix;
    @Getter
    private int maxSeatPerRow;
    public SeatMapHelper(List<Seat> seatResponses) {
        this.seatResponses = seatResponses;
        this.generateSeatMatrix();
        this.calculateMaxSeatPerRow();
    }
    private void generateSeatMatrix() {
        seatMatrix = new TreeMap<>();
        rootSeatMatrix = new TreeMap<>();
        for (Seat seat : seatResponses) {
            seatMatrix
                    .computeIfAbsent(seat.getRow(), r -> new TreeMap<>())
                    .put(seat.getCol(), seat);
            rootSeatMatrix
                    .computeIfAbsent(seat.getRootRow(), r -> new TreeMap<>())
                    .computeIfAbsent(seat.getRootCol(), c -> new ArrayList<>())
                    .add(seat);
        }
    }
    private void calculateMaxSeatPerRow() {
        maxSeatPerRow = -1;
        seatMatrix.forEach((row, map) -> {
            if (map.size() >= maxSeatPerRow) {
                maxSeatPerRow = map.size();
            }
        });
    }
}
