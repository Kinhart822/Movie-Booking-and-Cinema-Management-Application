package vn.edu.usth.mcma.backend.module.concession.dto.request;

import lombok.Data;

@Data
public class FoodRequest {
    private String name;
    private String description;
    private Double unitPrice;
}
