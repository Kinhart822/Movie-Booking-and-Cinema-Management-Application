package vn.edu.usth.mcma.backend.module.movie.dto.response;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShowtimeOfMovieBySchedule {
    private Long scheduleId;
    private Instant startTime;
}
