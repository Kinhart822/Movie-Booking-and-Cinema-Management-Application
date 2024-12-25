package vn.edu.usth.mcma.backend.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import constants.ApiResponseCode;
import constants.SeatType;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;
import vn.edu.usth.mcma.backend.exception.BusinessException;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Data
@Validated
@NoArgsConstructor
public class SeatMapRequest {
    @NotNull
    private Long screenId;
    @NotNull
    private List<SeatPosition> seatPositions;
    @JsonIgnore
    private List<SeatPosition> namedSeatPositions;
    /*
     * grid for quick lookup
     * must be initialized using initSeatGrid()
     */
    @JsonIgnore
    private Map<Integer, Map<Integer, SeatPositionValidate>> seatGrid;

    @JsonCreator
    public SeatMapRequest(@JsonProperty("screenId") Long screenId, @JsonProperty("seatPositions") List<SeatPosition> seatPositions) {
        this.screenId = screenId;
        this.seatPositions = seatPositions;
        this.validateSeatMap();
        this.assignName();
    }

    // treemap is used to preserve sorted order -> able to iterate row-by-row, col-by-col
    private void initSeatGrid(List<SeatPositionValidate> validateList) {
        seatGrid = new TreeMap<>();
        for (SeatPositionValidate seat : validateList) {
            seatGrid
                    .computeIfAbsent(seat.getRow(), r -> new TreeMap<>())
                    .put(seat.getCol(), seat);
        }
    }

    public void assignName() {
        namedSeatPositions = new ArrayList<>();
        char[] alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
        AtomicInteger currentLetter = new AtomicInteger();
        seatGrid.forEach((row, columnMap) -> {
            if (columnMap
                    .values()
                    .stream()
                    .allMatch(seat -> seat.getTypeId() == SeatType.UNAVAILABLE.getId() || seat.getTypeId() == SeatType.AVAILABLE.getId())) {
                columnMap.forEach((col, seat) -> namedSeatPositions.add(createSeatPosition(seat)));
                return;
            }
            AtomicInteger currentNumber = new AtomicInteger(1);
            columnMap.forEach((col, seat) -> {
                if (seat.getTypeId() == SeatType.UNAVAILABLE.getId() || seat.getTypeId() == SeatType.AVAILABLE.getId()) {
                    namedSeatPositions.add(createSeatPosition(seat));
                    return;
                }
                namedSeatPositions.add(createSeatPosition(seat, alphabet[currentLetter.get()] + String.valueOf(currentNumber)));
                currentNumber.getAndIncrement();
            });
            currentLetter.getAndIncrement();
        });
    }

    public void validateSeatMap() {
        Set<Integer> seatTypeIds = SeatType.getIdMap().keySet();
        // check typeId existent -> map to seatValidate object -> sort (see overridden sorting method of SeatPositionValue)
        List<SeatPositionValidate> validateList = this
                .seatPositions
                .stream()
                .map(seat -> createSeatPositionValidate(seat, seatTypeIds))
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
    }

    private boolean validateRectangle(SeatPositionValidate startSeat, Map<Integer, Map<Integer, SeatPositionValidate>> seatGrid, Integer width, Integer length) {
        for (int row = startSeat.getRow(); row < startSeat.getRow() + length; row++) {
            for (int col = startSeat.getCol(); col < startSeat.getCol() + width; col++) {
                SeatPositionValidate seat = seatGrid.get(row).get(col);
                if (seat == null || seat.getTypeId() != startSeat.getTypeId()) {
                    return false;
                }
            }
        }
        for (int row = startSeat.getRow(); row < startSeat.getRow() + length; row++) {
            for (int col = startSeat.getCol(); col < startSeat.getCol() + width; col++) {
                SeatPositionValidate seat = seatGrid.get(row).get(col);
                if (seat != null) {
                    seat.setChecked(true);
                }
            }
        }
        return true;
    }

    private SeatPosition createSeatPosition(SeatPositionValidate seat) {
        return createSeatPosition(seat, null);
    }

    private SeatPosition createSeatPosition(SeatPositionValidate seat, String name) {
        return SeatPosition
                .builder()
                .row(seat.getRow())
                .col(seat.getCol())
                .typeId(seat.getTypeId())
                .name(name)
                .build();
    }

    private SeatPositionValidate createSeatPositionValidate(SeatPosition seat, Set<Integer> seatTypeIds) {
        if (!seatTypeIds.contains(seat.getTypeId())) {
            throw new BusinessException(ApiResponseCode.SEAT_TYPE_NOT_FOUND);
        }
        return SeatPositionValidate
                .builder()
                .row(seat.getRow())
                .col(seat.getCol())
                .typeId(seat.getTypeId())
                .build();
    }
}
