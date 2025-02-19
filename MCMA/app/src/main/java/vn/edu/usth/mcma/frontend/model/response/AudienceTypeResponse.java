package vn.edu.usth.mcma.frontend.model.response;

import lombok.Data;

@Data
public class AudienceTypeResponse {
    private String id;
    private String description;
    private Double unitPrice;
    private Integer ageLowerBound;
    private Integer ageHigherBound;
}
