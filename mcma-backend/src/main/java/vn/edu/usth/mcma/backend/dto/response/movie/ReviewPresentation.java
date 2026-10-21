package vn.edu.usth.mcma.backend.dto.response.movie;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReviewPresentation {
    private Long id;
    private Long userId;
    private String userComment;
    private Integer userVote;
}
