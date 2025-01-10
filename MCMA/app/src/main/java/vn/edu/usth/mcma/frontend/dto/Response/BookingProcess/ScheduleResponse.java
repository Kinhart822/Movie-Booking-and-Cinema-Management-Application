package vn.edu.usth.mcma.frontend.dto.Response.BookingProcess;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleResponse {
    private List<Integer> scheduleId;
    private List<String> date;
    private List<String> time;
}

