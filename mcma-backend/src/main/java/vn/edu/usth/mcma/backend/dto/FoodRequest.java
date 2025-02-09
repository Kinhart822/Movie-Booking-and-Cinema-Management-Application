package vn.edu.usth.mcma.backend.dto;

import lombok.Data;

@Data
public class FoodRequest {
    private String name;
    private String description;
    private Double unitPrice;
}
