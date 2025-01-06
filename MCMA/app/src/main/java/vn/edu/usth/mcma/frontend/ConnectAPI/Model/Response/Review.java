package vn.edu.usth.mcma.frontend.ConnectAPI.Model.Response;

import lombok.Data;

@Data
public class Review {
    private Long id;
    private String userComment;
    private Integer userVote;
}
