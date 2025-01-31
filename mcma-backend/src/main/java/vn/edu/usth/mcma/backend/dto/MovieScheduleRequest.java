package vn.edu.usth.mcma.backend.dto;

import lombok.Data;

import java.time.Instant;

@Data
public class MovieScheduleRequest {
    private Long screenId;
    private Long movieId;
    private Instant startDateTime;
}
