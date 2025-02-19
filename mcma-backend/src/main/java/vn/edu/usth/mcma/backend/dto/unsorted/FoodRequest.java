package vn.edu.usth.mcma.backend.dto.unsorted;

import lombok.Data;

@Data
public class FoodRequest {
    private String name;
    private String description;
    private Double unitPrice;
}
