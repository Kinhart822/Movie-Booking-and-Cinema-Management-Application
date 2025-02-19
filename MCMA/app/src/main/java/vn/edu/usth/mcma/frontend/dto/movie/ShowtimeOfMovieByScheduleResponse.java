package vn.edu.usth.mcma.frontend.dto.movie;

import lombok.Data;

@Data
public class ShowtimeOfMovieByScheduleResponse {
    private Long scheduleId;
    private String startTime;
}
