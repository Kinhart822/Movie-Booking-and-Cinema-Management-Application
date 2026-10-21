package vn.edu.usth.mcma.backend.dto.response.cinema;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleOfScreenResponse {
    private Long screenId;
    private String screenName;
    private List<ScheduleResponse> schedules;
}
