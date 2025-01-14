package vn.edu.usth.mcma.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HighRatingMovie {
    private Long id;
    private String name;
    private Integer length;
    private String publishDate;
    private String imageBase64;
    private String imageBackgroundBase64;
    private List<GenrePresentation> genres;
    private Double avgVote;
}
