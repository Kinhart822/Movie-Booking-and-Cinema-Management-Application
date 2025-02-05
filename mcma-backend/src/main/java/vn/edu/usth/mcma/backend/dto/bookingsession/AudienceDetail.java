package vn.edu.usth.mcma.backend.dto.bookingsession;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AudienceDetail {
    private String id;
    private String description;
    private Double unitPrice;
    private Integer ageLowerBound;
    private Integer ageHigherBound;
}
