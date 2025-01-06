package vn.edu.usth.mcma.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
/*
 * represents a schedule of one movie
 * should be used with List
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SchedulePresentation {
    private Long id;
    private Long screenId;
    private Long movieId;
    private String startTime;
    private String endTime;
}
