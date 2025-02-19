package vn.edu.usth.mcma.backend.dto.movie;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MovieDetailShort2 {
    private Long id;
    private String name;
    private Integer length;
    private String banner;
    private String rating;
}
