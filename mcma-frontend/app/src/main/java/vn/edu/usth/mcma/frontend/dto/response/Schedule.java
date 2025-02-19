package vn.edu.usth.mcma.frontend.dto.response;

import lombok.Data;
/*
 * represents a schedule of one movie
 * should be used with List
 */
@Data
public class Schedule {
    private Long id;
    private Long screenId;
    private Long movieId;
    private String startTime;
    private String endTime;
}
