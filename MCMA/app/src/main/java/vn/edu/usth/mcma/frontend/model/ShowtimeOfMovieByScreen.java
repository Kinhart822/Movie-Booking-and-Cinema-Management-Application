package vn.edu.usth.mcma.frontend.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShowtimeOfMovieByScreen {
    private Long screenId;
    private String screenType;
    private List<ShowtimeOfMovieBySchedule> showtimeOfMovieBySchedule;
}
