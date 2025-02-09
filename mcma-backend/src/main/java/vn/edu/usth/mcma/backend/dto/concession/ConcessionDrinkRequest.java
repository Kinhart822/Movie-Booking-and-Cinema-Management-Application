package vn.edu.usth.mcma.backend.dto.concession;

import lombok.Data;

@Data
public class ConcessionDrinkRequest {
    private Long drinkId;
    private Integer quantity;
}
