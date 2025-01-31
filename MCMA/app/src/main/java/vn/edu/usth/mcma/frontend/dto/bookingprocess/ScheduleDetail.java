package vn.edu.usth.mcma.frontend.dto.bookingprocess;

import lombok.Data;

@Data
public class ScheduleDetail {
    private Long scheduleId;
    private String cinemaName;
    private String screenName;
    private String startDateTime;
    private String endDateTime;
    private String movieName;
    private String rating;
    private String screenType;
}
