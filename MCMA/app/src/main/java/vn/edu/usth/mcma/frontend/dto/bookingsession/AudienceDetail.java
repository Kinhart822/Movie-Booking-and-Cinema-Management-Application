package vn.edu.usth.mcma.frontend.dto.bookingsession;

import lombok.Data;

@Data
public class AudienceDetail {
    private String id;
    private String description;
    private Double unitPrice;
    private Integer ageLowerBound;
    private Integer ageHigherBound;
}
