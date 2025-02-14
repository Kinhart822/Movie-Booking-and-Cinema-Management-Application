package vn.edu.usth.mcma.backend.dto.admin.dto.movie;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FilterMovie {
    private Long movieId;
    private String movieName;
}
