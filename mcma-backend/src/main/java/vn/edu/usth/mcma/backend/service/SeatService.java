package vn.edu.usth.mcma.backend.service;

import constants.CommonStatus;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.edu.usth.mcma.backend.dto.SeatMapRequest;
import vn.edu.usth.mcma.backend.dto.SeatPosition;
import vn.edu.usth.mcma.backend.entity.Seat;
import vn.edu.usth.mcma.backend.entity.SeatPK;
import vn.edu.usth.mcma.backend.exception.ApiResponse;
import vn.edu.usth.mcma.backend.repository.SeatRepository;
import vn.edu.usth.mcma.backend.security.JwtUtil;

import java.time.Instant;
import java.util.List;

@Transactional
@Service
public class SeatService extends AbstractService<Seat, SeatPK> {
    private final SeatRepository seatRepository;
    private final JwtUtil jwtUtil;
    public SeatService(SeatRepository seatRepository, JwtUtil jwtUtil) {
        super(seatRepository);
        this.seatRepository = seatRepository;
        this.jwtUtil = jwtUtil;
    }
    public ApiResponse createSeatMap(SeatMapRequest request, HttpServletRequest hsRequest) {
        Long userId = jwtUtil.getUserIdFromToken(hsRequest);
        List<Seat> seats = request
                .getSeatPositions()
                .stream()
                .map(pos -> Seat
                        .builder()
                        .id(SeatPK
                                .builder()
                                .screenId(request.getScreenId())
                                .row(pos.getRow())
                                .column(pos.getCol())
                                .build())
                        .typeId(pos.getTypeId())
                        .isAvailable(false)
                        .build())
                .toList();
        seatRepository.saveAll(seats);
        return this.successResponse();
    }
    private List<Seat> assignName(List<Seat> seats) {
        return seats;
    }
    public List<Seat> findAll(String query, Pageable pageable) {
        return seatRepository.findAllByNameContaining(query, pageable);
    }
    public ApiResponse updateSeat(Long id, SeatMapRequest request, HttpServletRequest hsRequest) {
        Long userId = jwtUtil.getUserIdFromToken(hsRequest);
        Seat seat = findById(id);
        // changing cinemaId is not allowed think about it :)
        seat.setName(request.getName());
        seat.setTypeId(request.getTypeId());
        seat.setLastModifiedBy(userId);
        seat.setLastModifiedDate(Instant.now());
        seatRepository.save(seat);
        return this.successResponse();
    }
    public ApiResponse deleteSeat(Long id, HttpServletRequest hsRequest) {
        Long userId = jwtUtil.getUserIdFromToken(hsRequest);
        Seat seat = findById(id);
        seat.setStatus(CommonStatus.DELETED.getStatus());
        seat.setLastModifiedBy(userId);
        seat.setLastModifiedDate(Instant.now());
        seatRepository.save(seat);
        return this.successResponse();
    }
}
