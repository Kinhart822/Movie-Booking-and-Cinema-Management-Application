package vn.edu.usth.mcma.backend.module.concession.dto.request;

import lombok.Data;

@Data
public class ConcessionFoodRequest {
    private Long foodId;
    private Integer quantity;
}
