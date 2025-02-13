package vn.edu.usth.mcma.backend.dto.cinema;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleOfScreenResponse {
    private Long screenId;
    private String screenName;
    private List<ScheduleResponse> schedules;
}
