package vn.edu.usth.mcma.backend.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import vn.edu.usth.mcma.backend.dto.SeatMapRequest;
import vn.edu.usth.mcma.backend.dto.SeatPosition;

import java.util.ArrayList;
import java.util.List;

public class SeatTest {
    private SeatMapRequest request;
    private List<SeatPosition> seatPositions;
    @BeforeEach
    void init() {
        seatPositions = new ArrayList<>();
        seatPositions.add(SeatPosition.builder().row(1).col(1).typeId(-1).build());
        seatPositions.add(SeatPosition.builder().row(1).col(2).typeId(0).build());
        seatPositions.add(SeatPosition.builder().row(1).col(3).typeId(-1).build());
        seatPositions.add(SeatPosition.builder().row(1).col(4).typeId(0).build());
        seatPositions.add(SeatPosition.builder().row(2).col(1).typeId(1).build());
        seatPositions.add(SeatPosition.builder().row(2).col(2).typeId(2).build());
        seatPositions.add(SeatPosition.builder().row(2).col(3).typeId(1).build());
        seatPositions.add(SeatPosition.builder().row(2).col(4).typeId(-1).build());
        seatPositions.add(SeatPosition.builder().row(3).col(1).typeId(3).build());
        seatPositions.add(SeatPosition.builder().row(3).col(2).typeId(3).build());
        seatPositions.add(SeatPosition.builder().row(3).col(3).typeId(4).build());
        seatPositions.add(SeatPosition.builder().row(3).col(4).typeId(4).build());
        seatPositions.add(SeatPosition.builder().row(4).col(1).typeId(1).build());
        seatPositions.add(SeatPosition.builder().row(4).col(2).typeId(1).build());
        seatPositions.add(SeatPosition.builder().row(4).col(3).typeId(4).build());
        seatPositions.add(SeatPosition.builder().row(4).col(4).typeId(4).build());
        seatPositions.add(SeatPosition.builder().row(5).col(1).typeId(0).build());
        seatPositions.add(SeatPosition.builder().row(5).col(2).typeId(0).build());
        seatPositions.add(SeatPosition.builder().row(5).col(3).typeId(4).build());
        seatPositions.add(SeatPosition.builder().row(5).col(4).typeId(4).build());
        seatPositions.add(SeatPosition.builder().row(6).col(1).typeId(0).build());
        seatPositions.add(SeatPosition.builder().row(6).col(2).typeId(0).build());
        seatPositions.add(SeatPosition.builder().row(6).col(3).typeId(-1).build());
        seatPositions.add(SeatPosition.builder().row(6).col(4).typeId(-1).build());
    }

    @Test
    void test1() {
        request = new SeatMapRequest(seatPositions);
        System.out.println(request.getNamedSeatPositions());
    }

}
