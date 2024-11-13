package vn.edu.usth.mcma.service.admin.service.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieRequestDTO {
    private String title;
    private String description;
    private Integer length;
    private Instant datePublish;
    private Long ratingId;
    private String trailerLink;
    private Long genreId;
}
