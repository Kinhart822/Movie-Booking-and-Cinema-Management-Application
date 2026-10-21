package vn.edu.usth.mcma.backend.module.concession.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DrinkResponse {
    private Long id;
    private String name;
    private String description;
    private Double unitPrice;
    private Integer quantity;
}
