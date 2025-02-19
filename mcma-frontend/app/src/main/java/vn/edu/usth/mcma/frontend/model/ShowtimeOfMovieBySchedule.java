package vn.edu.usth.mcma.frontend.model;

import java.time.LocalTime;

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
    private LocalTime time;
}
