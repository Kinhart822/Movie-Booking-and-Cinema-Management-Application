package vn.edu.usth.mcma.backend.service;

import constants.ApiResponseCode;
import constants.SeatType;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import vn.edu.usth.mcma.backend.dto.SeatHelperInput;
import vn.edu.usth.mcma.backend.dto.SeatResponse;
import vn.edu.usth.mcma.backend.dto.SeatTile;
import vn.edu.usth.mcma.backend.dto.SeatTypeResponse;
import vn.edu.usth.mcma.backend.entity.Screen;
import vn.edu.usth.mcma.backend.entity.Seat;
import vn.edu.usth.mcma.backend.entity.SeatPK;
import vn.edu.usth.mcma.backend.exception.ApiResponse;
import vn.edu.usth.mcma.backend.exception.BusinessException;
import vn.edu.usth.mcma.backend.helper.SeatHelper;
import vn.edu.usth.mcma.backend.repository.ScreenRepository;
import vn.edu.usth.mcma.backend.repository.SeatRepository;
import vn.edu.usth.mcma.backend.repository.SeatTypeRepository;
import vn.edu.usth.mcma.backend.security.JwtHelper;

import java.time.Instant;
import java.util.*;

@Transactional
@Service
@AllArgsConstructor
public class SeatService {
    private final SeatRepository seatRepository;
    private final ScreenRepository screenRepository;
    private final JwtHelper jwtHelper;
    private final SeatTypeRepository seatTypeRepository;

    public ApiResponse initSeatMap(Long screenId, List<SeatHelperInput> seatHelperInputs) {
        Screen screen = screenRepository
                .findById(screenId)
                .orElseThrow(() -> new BusinessException(ApiResponseCode.ENTITY_NOT_FOUND));
        // check initiated
        if (!findSeatMapByScreenId(screenId).isEmpty()) {
            throw new BusinessException(ApiResponseCode.INITIATED_SEAT_MAP);
        }
        Long userId = jwtHelper.getIdUserRequesting();
        Instant now = Instant.now();
        Map<String, vn.edu.usth.mcma.backend.entity.SeatType> idSeatTypeMap = new HashMap<>();
        seatTypeRepository.findAll().forEach(seatType -> idSeatTypeMap.put(seatType.getId(), seatType));
        SeatHelper seatHelper = new SeatHelper(seatHelperInputs, idSeatTypeMap);
        seatRepository.saveAll(seatHelper
                .getSeatHelperOutputs() // sorted btw
                .stream()
                .map(output -> Seat
                        .builder()
                        .id(SeatPK
                                .builder()
                                .screen(screen)
                                .row(output.getRow())
                                .col(output.getCol())
                                .build())
                        .rootRow(output.getRootRow())
                        .rootCol(output.getRootCol())
                        .seatType(idSeatTypeMap.get(output.getTypeId()))
                        .name(output.getName())
                        .createdBy(userId)
                        .createdDate(now)
                        .lastModifiedBy(userId)
                        .lastModifiedDate(now)
                        .build())
                .toList());
        return ApiResponse.ok();
    }

    public List<SeatResponse> findSeatMapByScreenId(Long screenId) {
        return seatRepository
                .findAllByScreen(screenRepository
                        .findById(screenId)
                        .orElseThrow(() -> new BusinessException(ApiResponseCode.ENTITY_NOT_FOUND)))
                .stream()
                .map(s -> SeatResponse
                        .builder()
                        .row(s.getId().getRow())
                        .col(s.getId().getCol())
                        .typeId(s.getSeatType().getId())
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
        Long userId = jwtHelper.getIdUserRequesting();
        Instant now = Instant.now();
        Map<String, vn.edu.usth.mcma.backend.entity.SeatType> idSeatTypeMap = new HashMap<>();
        seatTypeRepository.findAll().forEach(seatType -> idSeatTypeMap.put(seatType.getId(), seatType));
        SeatHelper seatHelper = new SeatHelper(seatHelperInputs, idSeatTypeMap);
        Map<Integer, Map<Integer, SeatTile>> seatGrid = seatHelper.getSeatGrid();
        List<Seat> seats = seatRepository.findAllByScreen(screen);
        // a for loop to detect mutation of type = -1
        for (Seat seat : seats) {
            Integer row = seat.getId().getRow();
            Integer col = seat.getId().getCol();
            String typeId = seat.getSeatType().getId();
            String newTypeId = seatGrid.get(row).get(col).getTypeId();
            // TODO: low priority: better response for error: seat with type -1 at cannot be mutated
            if (Objects.equals(typeId, "NOT_PLACEABLE") && !typeId.equals(newTypeId)) {
                throw new BusinessException(ApiResponseCode.INVALID_SEAT_MAP);
            }
            if (Objects.equals(newTypeId, "NOT_PLACEABLE") && !typeId.equals(newTypeId)) {
                throw new BusinessException(ApiResponseCode.INVALID_SEAT_MAP);
            }
        }
        // begin update
        List<Seat> updatedSeats = new ArrayList<>();
        for (Seat seat : seats) {
            Integer row = seat.getId().getRow();
            Integer col = seat.getId().getCol();
            SeatTile tile = seatGrid.get(row).get(col);
            Seat updatedSeat = seat
                    .toBuilder()
                    .seatType(idSeatTypeMap.get(tile.getTypeId()))
                    .rootRow(tile.getRootRow())
                    .rootCol(tile.getRootCol())
                    .name(tile.getName())
                    .lastModifiedBy(userId)
                    .lastModifiedDate(now)
                    .build();
            updatedSeats.add(updatedSeat);
        }
        seatRepository.saveAll(updatedSeats);
        return ApiResponse.ok();
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
