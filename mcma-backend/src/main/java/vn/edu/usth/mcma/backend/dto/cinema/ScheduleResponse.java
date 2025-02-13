package vn.edu.usth.mcma.backend.dto.cinema;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleResponse {
    private Long scheduleId;
    private Long movieId;
    private String movieName;
    private Integer movieLength;
    private Instant startDateTime;
    private Integer status;
}
