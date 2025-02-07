package vn.edu.usth.mcma.backend.helper;

import constants.ApiResponseCode;
import lombok.Getter;
import vn.edu.usth.mcma.backend.dto.SeatHelperInput;
import vn.edu.usth.mcma.backend.dto.SeatHelperOutput;
import vn.edu.usth.mcma.backend.dto.SeatTile;
import vn.edu.usth.mcma.backend.entity.SeatType;
import vn.edu.usth.mcma.backend.exception.BusinessException;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class SeatHelper {
    private final List<SeatHelperInput> seatHelperInputs;
    private final Map<String, SeatType> idSeatTypeMap;
    @Getter
    private List<SeatHelperOutput> seatHelperOutputs;
    /*
     * grid for quick lookup
     * this is initialized in validateSeatMap() using initSeatGrid()
     */
    @Getter
    private Map<Integer, Map<Integer, SeatTile>> seatGrid;
    public SeatHelper(
            List<SeatHelperInput> seatHelperInputs,
            Map<String, SeatType> idSeatTypeMap) {
        this.seatHelperInputs = seatHelperInputs;
        this.idSeatTypeMap = idSeatTypeMap;
        this.validateSeatMap();
        this.assignName();
    }
    private void validateSeatMap() {
        Set<String> seatTypeIds = idSeatTypeMap.keySet();
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
            //use equivalent seat in seatGrid to see if seat is checked
            if (seatGrid.get(seat.getRow()).get(seat.getCol()).isChecked()) {
                continue;
            }
            // already checked above if seat type exists
            SeatType seatType = idSeatTypeMap.get(seat.getTypeId());
            // validate rectangle
            if (!validateRectangle(seat, seatGrid, seatType.getWidth(), seatType.getHeight())) {
                throw new BusinessException(ApiResponseCode.INVALID_SEAT_MAP);
            }
        }
    }
    private SeatTile verifyAndConvertFromRequestToTile(SeatHelperInput seat, Set<String> seatTypeIds) {
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
        int rootRow = startSeat.getRow();
        int rootCol = startSeat.getCol();
        // check if all seat in rectangle have the same type
        for (int row = rootRow; row < rootRow + length; row++) {
            for (int col = rootCol; col < rootCol + width; col++) {
                SeatTile seat = seatGrid.get(row).get(col);
                if (seat == null || !Objects.equals(seat.getTypeId(), startSeat.getTypeId())) {
                    return false;
                }
            }
        }
        // if pass the check then set all seat in rectangle checked
        for (int row = rootRow; row < rootRow + length; row++) {
            for (int col = rootCol; col < rootCol + width; col++) {
                SeatTile seat = seatGrid.get(row).get(col);
                if (seat != null) {
                    seatGrid
                            .get(row)
                            .put(col, seat
                                    .toBuilder()
                                    .isChecked(true)
                                    .rootRow(rootRow)
                                    .rootCol(rootCol)
                                    .build());
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
                    .allMatch(seat -> Objects.equals(seat.getTypeId(), "NOT_PLACEABLE") || Objects.equals(seat.getTypeId(), "PLACEABLE"))) {
                columnMap.forEach((col, seat) -> seatHelperOutputs.add(createSeatResponse(seat)));
                return;
            }
            AtomicInteger currentNumber = new AtomicInteger(0);
            columnMap.forEach((col, seat) -> {
                if (Objects.equals(seat.getTypeId(), "NOT_PLACEABLE") || Objects.equals(seat.getTypeId(), "PLACEABLE")) {
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
                .rootRow(seat.getRootRow())
                .rootCol(seat.getRootCol())
                .typeId(seat.getTypeId())
                .name(name)
                .build();
    }
}
