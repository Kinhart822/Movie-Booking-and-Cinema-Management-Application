package vn.edu.usth.mcma.backend.dto.movie;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HighRatingMovie {
    private Long id;
    private String name;
    private String poster;
    private Double avgVote;
}
