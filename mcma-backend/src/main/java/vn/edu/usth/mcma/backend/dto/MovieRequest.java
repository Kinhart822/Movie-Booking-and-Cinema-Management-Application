package vn.edu.usth.mcma.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MovieRequest {
    private String name;
    private String overview;
    private Integer length;
    private String poster;
    private String banner;
    private String publishDate;
    private String trailerUrl;
    private Long ratingId;
    private Integer status;
}
