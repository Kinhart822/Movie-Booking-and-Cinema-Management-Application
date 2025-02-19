package vn.edu.usth.mcma.frontend.model.item;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ItemsOrderedItem {
    private int quantity;
    private String name;
    private double totalPrice;
}
