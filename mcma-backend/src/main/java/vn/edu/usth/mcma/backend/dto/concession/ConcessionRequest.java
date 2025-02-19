package vn.edu.usth.mcma.backend.dto.concession;

import lombok.Data;

import java.util.List;

@Data
public class ConcessionRequest {
    private String name;
    private String description;
    private Double comboPrice;
    private String imageBase64;
    private List<ConcessionFoodRequest> foods;
    private List<ConcessionDrinkRequest> drinks;
}
