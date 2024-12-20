package vn.edu.usth.mcma.backend.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import vn.edu.usth.mcma.backend.entity.SeatType;

import java.util.List;

@Data
public class SeatMapRequest {
    private Long screenId;
    @NotNull
    private List<SeatPosition> seatPositions;
    private boolean validateSeatMap() {
        List<SeatType> allTypes = seatType
        // see overridden sorting method of SeatPositionValue
        List<SeatPositionValidate> validateList = this
                .seatPositions
                .stream()
                .map(seatPos -> SeatPositionValidate
                        .builder()
                        .row(seatPos.getRow())
                        .col(seatPos.getCol())
                        .typeId(seatPos.getTypeId())
                        .build())
                .sorted()
                .toList();
        for (SeatPositionValidate validate : validateList) {
            if (validate.isChecked()) {
                continue;
            }

        }
    }
}
