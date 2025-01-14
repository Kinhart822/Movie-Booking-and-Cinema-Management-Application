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
    private String description;
    private Integer length;
    private String imageBase64;
    private String backgroundImageBase64;
    private String publishDate;
    private String trailerUrl;
    private Long ratingId;
    private Integer status;
}
