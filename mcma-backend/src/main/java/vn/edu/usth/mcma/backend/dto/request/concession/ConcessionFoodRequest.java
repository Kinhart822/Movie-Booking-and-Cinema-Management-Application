package vn.edu.usth.mcma.backend.dto.request.concession;

import lombok.Data;

@Data
public class ConcessionFoodRequest {
    private Long foodId;
    private Integer quantity;
}
