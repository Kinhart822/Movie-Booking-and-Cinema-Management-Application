package vn.edu.usth.mcma.frontend.dto.response;

import lombok.Data;

@Data
public class HighRatingMovie {
    private Integer id;
    private String name;
    private String imageBase64;
    private Double avgVote;
}
