package vn.edu.usth.mcma.backend.dto.request.concession;

import lombok.Data;

@Data
public class ConcessionDrinkRequest {
    private Long drinkId;
    private Integer quantity;
}
