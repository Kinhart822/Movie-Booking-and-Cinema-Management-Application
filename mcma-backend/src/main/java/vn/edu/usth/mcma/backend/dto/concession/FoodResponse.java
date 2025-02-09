package vn.edu.usth.mcma.backend.dto.concession;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FoodResponse {
    private Long id;
    private String name;
    private String description;
    private Double unitPrice;
    private Integer quantity;
}
