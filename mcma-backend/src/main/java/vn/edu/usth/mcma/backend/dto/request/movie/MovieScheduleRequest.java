package vn.edu.usth.mcma.backend.dto.request.movie;

import java.time.Instant;

import lombok.Data;

/*
 * if scheduleId is provided -> update existing schedule
 * else create new schedule
 */
@Data
public class MovieScheduleRequest {
    private Long scheduleId;
    private Long screenId;
    private Long movieId;
    private Instant startDateTime;
    private Integer status;
}
