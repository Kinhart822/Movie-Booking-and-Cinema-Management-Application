package vn.edu.usth.mcma.backend.module.concession.dto.request;

import lombok.Data;

@Data
public class ConcessionDrinkRequest {
    private Long drinkId;
    private Integer quantity;
}
