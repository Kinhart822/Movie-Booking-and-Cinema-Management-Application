package vn.edu.usth.mcma.frontend.model.item;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConcessionItem {
    private Long id;
    private String name;
    private String description;
    private Double comboPrice;
    private String imageBase64;
    private int quantity;
}
