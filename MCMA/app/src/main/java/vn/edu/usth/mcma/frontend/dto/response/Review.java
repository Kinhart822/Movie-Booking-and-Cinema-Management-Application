package vn.edu.usth.mcma.frontend.dto.response;

import lombok.Data;

@Data
public class Review {
    private Long id;
    private String userComment;
    private Integer userVote;
}
