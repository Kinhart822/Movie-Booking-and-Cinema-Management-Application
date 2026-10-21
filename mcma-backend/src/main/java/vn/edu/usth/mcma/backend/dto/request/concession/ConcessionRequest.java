package vn.edu.usth.mcma.backend.dto.request.concession;

import java.util.List;

import lombok.Data;

@Data
public class ConcessionRequest {
    private String name;
    private String description;
    private Double comboPrice;
    private String imageBase64;
    private List<ConcessionFoodRequest> foods;
    private List<ConcessionDrinkRequest> drinks;
}
