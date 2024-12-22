package vn.edu.usth.mcma.backend.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import constants.ApiResponseCode;
import constants.SeatType;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import vn.edu.usth.mcma.backend.exception.BusinessException;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Data
public class SeatMapRequest {
    private Long screenId;
    @NotNull
    private List<SeatPosition> seatPositions;
    /*
     * grid for quick lookup
     * must be initialized using initSeatGrid()
     */
    @JsonIgnore
    private Map<Integer, Map<Integer, SeatPositionValidate>> seatGrid;
    // treemap is used to preserve sorted order -> able to iterate row-by-row, col-by-col
    private void initSeatGrid(List<SeatPositionValidate> validateList) {
        seatGrid = new TreeMap<>();
        for (SeatPositionValidate seat : validateList) {
            seatGrid
                    .computeIfAbsent(seat.getRow(), r -> new TreeMap<>())
                    .put(seat.getCol(), seat);
        }
    }
    public SeatMapRequest assignName() {
        char[] alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
        AtomicInteger currentLetter = new AtomicInteger();
        seatGrid.forEach((row, columnMap) -> {
            if (columnMap
                    .values()
                    .stream()
                    .allMatch(seat -> seat.getTypeId() == -1 || seat.getTypeId() == 0)) {
                currentLetter.getAndIncrement();
                return;
            }
            columnMap.forEach((col, seat) -> {
                seat
            });
        });
        return this;
    }
    public SeatMapRequest validateSeatMap() {
        Set<Integer> seatIds = SeatType.getIdMap().keySet();
        // check typeId existent -> map to seatValidate object -> sort (see overridden sorting method of SeatPositionValue)
        List<SeatPositionValidate> validateList = this
                .seatPositions
                .stream()
                .map(seatPos -> SeatPositionValidate
                        .builder()
                        .row(seatPos.getRow())
                        .col(seatPos.getCol())
                        .typeId(seatIds.contains(seatPos.getTypeId()) ? seatPos.getTypeId() : throwException(new BusinessException(ApiResponseCode.SEAT_TYPE_NOT_FOUND)))
                        .build())
                .sorted()
                .toList();

        initSeatGrid(validateList);

        // iterate through each seat and validate rectangles
        for (SeatPositionValidate seat : validateList) {
            if (seat.isChecked()) {
                continue;
            }
            // already checked above if seat type exists
            SeatType seatType = SeatType.getById(seat.getTypeId());
            // validate rectangle
            if (!validateRectangle(seat, seatGrid, seatType.getWidth(), seatType.getLength())) {
                throw new BusinessException(ApiResponseCode.INVALID_SEAT_MAP);
            }
        }
        return assignName();
    }

    private boolean validateRectangle(SeatPositionValidate startSeat, Map<Integer, Map<Integer, SeatPositionValidate>> seatGrid, Integer width, Integer length) {
        for (int row = startSeat.getRow(); row < startSeat.getRow() + width; row++) {
            for (int col = startSeat.getCol(); col < startSeat.getCol() + length; col++) {
                SeatPositionValidate seat = seatGrid.get(row).get(col);
                if (seat == null || seat.isChecked() || seat.getTypeId() != startSeat.getTypeId()) {
                    return false;
                }
            }
        }
        for (int row = startSeat.getRow(); row < startSeat.getRow() + width; row++) {
            for (int col = startSeat.getCol(); col < startSeat.getCol() + length; col++) {
                SeatPositionValidate seat = seatGrid.get(row).get(col);
                if (seat != null) {
                    seat.setChecked(true);
                }
            }
        }
        return true;
    }

    private Integer throwException(BusinessException exception) {
        throw exception;
    }
}
