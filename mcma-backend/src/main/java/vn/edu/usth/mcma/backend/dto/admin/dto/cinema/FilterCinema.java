package vn.edu.usth.mcma.backend.dto.admin.dto.cinema;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FilterCinema {
    private Long cinemaId;
    private String cinemaName;
}
