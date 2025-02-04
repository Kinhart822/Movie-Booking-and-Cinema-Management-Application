package vn.edu.usth.mcma.frontend.utils.helper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

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
    @Getter
    private String nearestRow;
    @Getter
    private String farthestRow;
    public SeatMapHelper(List<Seat> seatResponses) {
        this.seatResponses = seatResponses;
        this.generateSeatMatrix();
        this.calculateMaxSeatPerRowAndNearestRowAndFarthestRow();
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
    private void calculateMaxSeatPerRowAndNearestRowAndFarthestRow() {
        maxSeatPerRow = -1;
        nearestRow = null;
        farthestRow = null;
        TreeSet<String> rowLetters = new TreeSet<>();

        seatMatrix.forEach((row, colSeatMap) -> {
            maxSeatPerRow = Math.max(maxSeatPerRow, colSeatMap.size());

            colSeatMap.forEach((col, seat) -> {
                if (seat.getName() != null) {
                    String letter = seat.getName().substring(0, 1);
                    rowLetters.add(letter);
                }
            });
        });

        if (!rowLetters.isEmpty()) {
            nearestRow = rowLetters.first();
            farthestRow = rowLetters.last();
        }
    }

}
