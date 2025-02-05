package vn.edu.usth.mcma.backend.dto.bookingsession;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

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
    private String screenType;
}
