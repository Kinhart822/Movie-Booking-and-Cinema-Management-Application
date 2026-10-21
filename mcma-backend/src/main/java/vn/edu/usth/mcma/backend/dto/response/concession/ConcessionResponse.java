package vn.edu.usth.mcma.backend.dto.response.concession;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConcessionResponse {
    private Long id;
    private String name;
    private String description;
    private Double comboPrice;
    private String imageBase64;
    private List<FoodResponse> foods;
    private List<DrinkResponse> drinks;
}
