package vn.edu.usth.mcma.frontend.dto.movie;

import java.time.Instant;

import lombok.Data;

@Data
public class ShowtimeOfMovieBySchedule {
    private Long scheduleId;
    private String startTime;
}
