package vn.edu.usth.mcma.backend.service;

import constants.ApiResponseCode;
import constants.SeatType;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import vn.edu.usth.mcma.backend.dto.SeatMapRequest;
import vn.edu.usth.mcma.backend.dto.SeatMapResponse;
import vn.edu.usth.mcma.backend.dto.SeatPosition;
import vn.edu.usth.mcma.backend.dto.SeatTile;
import vn.edu.usth.mcma.backend.entity.Screen;
import vn.edu.usth.mcma.backend.entity.Seat;
import vn.edu.usth.mcma.backend.entity.SeatPK;
import vn.edu.usth.mcma.backend.exception.ApiResponse;
import vn.edu.usth.mcma.backend.exception.BusinessException;
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
    private final boolean DEFAULT_SEAT_AVAILABILITY = true;

    public ApiResponse initSeatMap(Long screenId, SeatMapRequest request) {
        Screen screen = screenRepository
                .findById(screenId)
                .orElseThrow(() -> new BusinessException(ApiResponseCode.ENTITY_NOT_FOUND));
        // check initiated
        if (!findSeatMapByScreenId(screenId).getSeatPositions().isEmpty()) {
            throw new BusinessException(ApiResponseCode.INITIATED_SEAT_MAP);
        }
        Long userId = jwtUtil.getUserIdFromToken();
        Instant now = Instant.now();
        seatRepository.saveAll(request
                .getNamedSeatPositions() // sorted btw
                .stream()
                .map(pos -> Seat
                        .builder()
                        .pk(SeatPK
                                .builder()
                                .screenId(screen.getId())
                                .row(pos.getRow())
                                .column(pos.getCol())
                                .build())
                        .typeId(pos.getTypeId())
                        .name(pos.getName())
                        .isAvailable(DEFAULT_SEAT_AVAILABILITY)
                        .createdBy(userId)
                        .createdDate(now)
                        .lastModifiedBy(userId)
                        .lastModifiedDate(now)
                        .build())
                .toList());
        return ApiResponse.success();
    }

    public SeatMapResponse findSeatMapByScreenId(Long screenId) {
        return SeatMapResponse
                .builder()
                .seatPositions(seatRepository
                        .findAllByScreenId(screenId)
                        .stream()
                        .map(s -> SeatPosition
                                .builder()
                                .row(s.getPk().getRow())
                                .col(s.getPk().getColumn())
                                .typeId(s.getTypeId())
                                .name(s.getName())
                                .build())
                        .toList())
                .build();
    }
    public ApiResponse updateSeatMap(Long screenId, SeatMapRequest request) {
        Screen screen = screenRepository
                .findById(screenId)
                .orElseThrow(() -> new BusinessException(ApiResponseCode.ENTITY_NOT_FOUND));
        if (!screen.isMutable()) {
            throw new BusinessException(ApiResponseCode.BUSY_SCREEN);
        }
        Long userId = jwtUtil.getUserIdFromToken();
        Instant now = Instant.now();
        Map<Integer, Map<Integer, SeatTile>> seatGrid = request.getSeatGrid();
        List<Seat> seats = seatRepository.findAllByScreenId(screenId);
        // a for loop to detect mutation of type = -1
        for (Seat seat : seats) {
            Integer row = seat.getPk().getRow();
            Integer col = seat.getPk().getColumn();
            Integer typeId = seat.getTypeId();
            Integer newTypeId = seatGrid.get(row).get(col).getTypeId();
            // TODO: low priority: better response for error: seat with type -1 at cannot be mutated
            if (typeId == SeatType.UNAVAILABLE.getId() && !typeId.equals(newTypeId)) {
                throw new BusinessException(ApiResponseCode.INVALID_SEAT_MAP);
            }
            if (newTypeId == SeatType.UNAVAILABLE.getId() && !typeId.equals(newTypeId)) {
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
                    .name(tile.getName())
                    .lastModifiedBy(userId)
                    .lastModifiedDate(now)
                    .build();
            updatedSeats.add(updatedSeat);
        }
        seatRepository.saveAll(updatedSeats);
        return ApiResponse.success();
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
