package vn.edu.usth.mcma.backend.module.cinema.dto.response;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
