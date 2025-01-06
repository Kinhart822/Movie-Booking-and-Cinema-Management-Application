package vn.edu.usth.mcma.frontend.ConnectAPI.Model.Response.BookingProcess;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.edu.usth.mcma.frontend.ConnectAPI.Enum.PerformerType;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleResponse {
    private List<Integer> scheduleId;
    private List<String> date;
    private List<String> time;
}

