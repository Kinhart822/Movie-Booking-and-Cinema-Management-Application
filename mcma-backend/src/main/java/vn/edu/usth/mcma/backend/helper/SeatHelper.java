package vn.edu.usth.mcma.backend.helper;

import constants.ApiResponseCode;
import constants.SeatType;
import lombok.Getter;
import vn.edu.usth.mcma.backend.dto.SeatHelperInput;
import vn.edu.usth.mcma.backend.dto.SeatHelperOutput;
import vn.edu.usth.mcma.backend.dto.SeatTile;
import vn.edu.usth.mcma.backend.exception.BusinessException;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class SeatHelper {
    private final List<SeatHelperInput> seatHelperInputs;
    @Getter
    private List<SeatHelperOutput> seatHelperOutputs;
    /*
     * grid for quick lookup
     * this is initialized in validateSeatMap() using initSeatGrid()
     */
    @Getter
    private Map<Integer, Map<Integer, SeatTile>> seatGrid;
    public SeatHelper(List<SeatHelperInput> seatHelperInputs) {
        this.seatHelperInputs = seatHelperInputs;
        this.validateSeatMap();
        this.assignName();
    }
    private void validateSeatMap() {
        Set<Integer> seatTypeIds = SeatType.getIdMap().keySet();
        // check typeId existent -> map to seatValidate object -> sort (see overridden sorting method of SeatTile)
        List<SeatTile> tiles = this
                .seatHelperInputs
                .stream()
                .map(seat -> verifyAndConvertFromRequestToTile(seat, seatTypeIds))
                .sorted()
                .toList();

        initSeatGrid(tiles);

        // iterate through each seat and validate rectangles
        for (SeatTile seat : tiles) {
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
    }
    private SeatTile verifyAndConvertFromRequestToTile(SeatHelperInput seat, Set<Integer> seatTypeIds) {
        if (!seatTypeIds.contains(seat.getTypeId())) {
            throw new BusinessException(ApiResponseCode.SEAT_TYPE_NOT_FOUND);
        }
        return SeatTile
                .builder()
                .row(seat.getRow())
                .col(seat.getCol())
                .typeId(seat.getTypeId())
                .build();
    }
    // treemap is used to preserve sorted order -> able to iterate row-by-row, col-by-col
    private void initSeatGrid(List<SeatTile> tiles) {
        seatGrid = new TreeMap<>();
        for (SeatTile seat : tiles) {
            seatGrid
                    .computeIfAbsent(seat.getRow(), r -> new TreeMap<>())
                    .put(seat.getCol(), seat);
        }
    }
    private boolean validateRectangle(SeatTile startSeat, Map<Integer, Map<Integer, SeatTile>> seatGrid, Integer width, Integer length) {
        // check if all seat in rectangle have the same type
        for (int row = startSeat.getRow(); row < startSeat.getRow() + length; row++) {
            for (int col = startSeat.getCol(); col < startSeat.getCol() + width; col++) {
                SeatTile seat = seatGrid.get(row).get(col);
                if (seat == null || seat.getTypeId() != startSeat.getTypeId()) {
                    return false;
                }
            }
        }
        // if pass the check then set all seat in rectangle checked
        for (int row = startSeat.getRow(); row < startSeat.getRow() + length; row++) {
            for (int col = startSeat.getCol(); col < startSeat.getCol() + width; col++) {
                SeatTile seat = seatGrid.get(row).get(col);
                if (seat != null) {
                    seat.setChecked(true);
                }
            }
        }
        return true;
    }
    // this will assignName for seatGrid and seatResponse
    private void assignName() {
        seatHelperOutputs = new ArrayList<>();
        char[] alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
        AtomicInteger currentLetter = new AtomicInteger();
        seatGrid.forEach((row, columnMap) -> {
            if (columnMap
                    .values()
                    .stream()
                    .allMatch(seat -> seat.getTypeId() == SeatType.UNAVAILABLE.getId() || seat.getTypeId() == SeatType.AVAILABLE.getId())) {
                columnMap.forEach((col, seat) -> seatHelperOutputs.add(createSeatResponse(seat)));
                return;
            }
            AtomicInteger currentNumber = new AtomicInteger(1);
            columnMap.forEach((col, seat) -> {
                if (seat.getTypeId() == SeatType.UNAVAILABLE.getId() || seat.getTypeId() == SeatType.AVAILABLE.getId()) {
                    seatHelperOutputs.add(createSeatResponse(seat));
                    return;
                }
                String name = alphabet[currentLetter.get()] + String.valueOf(currentNumber);
                seat.setName(name);
                seatHelperOutputs.add(createSeatResponse(seat, name));
                currentNumber.getAndIncrement();
            });
            currentLetter.getAndIncrement();
        });
    }
    private SeatHelperOutput createSeatResponse(SeatTile seat) {
        return createSeatResponse(seat, null);
    }
    private SeatHelperOutput createSeatResponse(SeatTile seat, String name) {
        return SeatHelperOutput
                .builder()
                .row(seat.getRow())
                .col(seat.getCol())
                .typeId(seat.getTypeId())
                .name(name)
                .build();
    }
}
