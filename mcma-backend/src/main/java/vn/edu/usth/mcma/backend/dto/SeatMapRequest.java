package vn.edu.usth.mcma.backend.dto;

import constants.ApiResponseCode;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import vn.edu.usth.mcma.backend.entity.SeatType;
import vn.edu.usth.mcma.backend.exception.BusinessException;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.lang.invoke.MethodHandles.throwException;

@Data
public class SeatMapRequest {
    private Long screenId;
    @NotNull
    private List<SeatPosition> seatPositions;
    private boolean validateSeatMap(Map<Long, SeatType> seatTypes) {
        Set<Long> seatIds = seatTypes.keySet();
        // check typeId existent -> map to validate object -> sort (see overridden sorting method of SeatPositionValue)
        List<SeatPositionValidate> validateList = this
                .seatPositions
                .stream()
                .map(seatPos -> SeatPositionValidate
                        .builder()
                        .row(seatPos.getRow())
                        .col(seatPos.getCol())
                        .typeId(seatIds.contains(seatPos.getTypeId()) ? seatPos.getTypeId() : throwException(new BusinessException(ApiResponseCode.ENTITY_NOT_FOUND)))
                        .build())
                .sorted()
                .toList();
        for (SeatPositionValidate validate : validateList) {
            if (validate.isChecked()) {
                continue;
            }

        }
        return true;
    }
    private Long throwException(BusinessException exception) {
        throw exception;
    }
}
