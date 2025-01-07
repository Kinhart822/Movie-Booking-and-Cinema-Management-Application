package vn.edu.usth.mcma.backend.service;

import constants.ApiResponseCode;
import constants.SeatAvailability;
import constants.SeatType;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import vn.edu.usth.mcma.backend.dto.SeatHelperInput;
import vn.edu.usth.mcma.backend.dto.SeatResponse;
import vn.edu.usth.mcma.backend.dto.SeatTile;
import vn.edu.usth.mcma.backend.dto.SeatTypePresentation;
import vn.edu.usth.mcma.backend.entity.Screen;
import vn.edu.usth.mcma.backend.entity.Seat;
import vn.edu.usth.mcma.backend.entity.SeatPK;
import vn.edu.usth.mcma.backend.exception.ApiResponse;
import vn.edu.usth.mcma.backend.exception.BusinessException;
import vn.edu.usth.mcma.backend.helper.SeatHelper;
import vn.edu.usth.mcma.backend.repository.ScreenRepository;
import vn.edu.usth.mcma.backend.repository.SeatRepository;
import vn.edu.usth.mcma.backend.security.JwtUtil;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Transactional
@Service
@AllArgsConstructor
public class SeatService {
    private final SeatRepository seatRepository;
    private final ScreenRepository screenRepository;
    private final JwtUtil jwtUtil;
    private final SeatAvailability DEFAULT_SEAT_AVAILABILITY = SeatAvailability.AVAILABLE;

    public ApiResponse initSeatMap(Long screenId, List<SeatHelperInput> seatHelperInputs) {
        Screen screen = screenRepository
                .findById(screenId)
                .orElseThrow(() -> new BusinessException(ApiResponseCode.ENTITY_NOT_FOUND));
        // check initiated
        if (!findSeatMapByScreenId(screenId).isEmpty()) {
            throw new BusinessException(ApiResponseCode.INITIATED_SEAT_MAP);
        }
        Long userId = jwtUtil.getUserIdFromToken();
        Instant now = Instant.now();
        SeatHelper seatHelper = new SeatHelper(seatHelperInputs);
        seatRepository.saveAll(seatHelper
                .getSeatHelperOutputs() // sorted btw
                .stream()
                .map(output -> Seat
                        .builder()
                        .pk(SeatPK
                                .builder()
                                .screenId(screen.getId())
                                .row(output.getRow())
                                .column(output.getCol())
                                .build())
                        .rootRow(output.getRootRow())
                        .rootCol(output.getRootCol())
                        .typeId(output.getTypeId())
                        .name(output.getName())
                        .availability(DEFAULT_SEAT_AVAILABILITY)
                        .createdBy(userId)
                        .createdDate(now)
                        .lastModifiedBy(userId)
                        .lastModifiedDate(now)
                        .build())
                .toList());
        return ApiResponse.success();
    }

    public List<SeatResponse> findSeatMapByScreenId(Long screenId) {
        return seatRepository
                .findAllByScreenId(screenId)
                .stream()
                .map(s -> SeatResponse
                        .builder()
                        .row(s.getPk().getRow())
                        .col(s.getPk().getColumn())
                        .typeId(s.getTypeId())
                        .name(s.getName())
                        .rootRow(s.getRootRow())
                        .rootCol(s.getRootCol())
                        .build())
                .toList();
    }
    public ApiResponse updateSeatMap(Long screenId, List<SeatHelperInput> seatHelperInputs) {
        Screen screen = screenRepository
                .findById(screenId)
                .orElseThrow(() -> new BusinessException(ApiResponseCode.ENTITY_NOT_FOUND));
        if (!screen.isMutable()) {
            throw new BusinessException(ApiResponseCode.BUSY_SCREEN);
        }
        Long userId = jwtUtil.getUserIdFromToken();
        Instant now = Instant.now();
        SeatHelper seatHelper = new SeatHelper(seatHelperInputs);
        Map<Integer, Map<Integer, SeatTile>> seatGrid = seatHelper.getSeatGrid();
        List<Seat> seats = seatRepository.findAllByScreenId(screenId);
        // a for loop to detect mutation of type = -1
        for (Seat seat : seats) {
            Integer row = seat.getPk().getRow();
            Integer col = seat.getPk().getColumn();
            Integer typeId = seat.getTypeId();
            Integer newTypeId = seatGrid.get(row).get(col).getTypeId();
            // TODO: low priority: better response for error: seat with type -1 at cannot be mutated
            if (typeId == SeatType.NOT_PLACEABLE.getId() && !typeId.equals(newTypeId)) {
                throw new BusinessException(ApiResponseCode.INVALID_SEAT_MAP);
            }
            if (newTypeId == SeatType.NOT_PLACEABLE.getId() && !typeId.equals(newTypeId)) {
                throw new BusinessException(ApiResponseCode.INVALID_SEAT_MAP);
            }
        }
        // begin update
        List<Seat> updatedSeats = new ArrayList<>();
        for (Seat seat : seats) {
            Integer row = seat.getPk().getRow();
            Integer col = seat.getPk().getColumn();
            SeatTile tile = seatGrid.get(row).get(col);
            Seat updatedSeat = seat
                    .toBuilder()
                    .typeId(tile.getTypeId())
                    .rootRow(tile.getRootRow())
                    .rootCol(tile.getRootCol())
                    .name(tile.getName())
                    .lastModifiedBy(userId)
                    .lastModifiedDate(now)
                    .build();
            updatedSeats.add(updatedSeat);
        }
        seatRepository.saveAll(updatedSeats);
        return ApiResponse.success();
    }

    public List<SeatTypePresentation> getAllSeatTypes() {
        List<SeatTypePresentation> seatTypes = new ArrayList<>();
        SeatType.getIdMap().forEach((id, seatType) ->  seatTypes.add(SeatTypePresentation
                .builder()
                .id(seatType.getId())
                .name(seatType.name())
                .description(seatType.getDescription())
                .width(seatType.getWidth())
                .length(seatType.getLength())
                .price(seatType.getPrice())
                .build()));
        return seatTypes;
    }
//    public ApiResponse deleteSeat(Long id) {
//        Long userId = jwtUtil.getUserIdFromToken(hsRequest);
//        Seat seat = findById(id);
//        seat.setStatus(CommonStatus.DELETED.getStatus());
//        seat.setLastModifiedBy(userId);
//        seat.setLastModifiedDate(Instant.now());
//        seatRepository.save(seat);
//        return ApiResponse.success();
//    }
}
