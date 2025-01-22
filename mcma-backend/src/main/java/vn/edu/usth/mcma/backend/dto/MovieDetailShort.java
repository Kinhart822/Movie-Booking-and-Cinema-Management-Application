package vn.edu.usth.mcma.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MovieDetailShort {
    private Long id;
    private String name;
    private Integer length;
    private String imageBase64;
    private String rating;
}
