package vn.edu.usth.mcma.backend.module.booking.dto.response;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleDetail {
    private Long scheduleId;
    private String cinemaName;
    private String screenName;
    private Instant startDateTime;
    private Instant endDateTime;
    private String movieName;
    private String rating;
    private String ratingDescription;
    private String screenType;
}
