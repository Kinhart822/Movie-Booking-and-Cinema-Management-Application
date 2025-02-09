package vn.edu.usth.mcma.backend.dto.concession;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.edu.usth.mcma.backend.entity.*;

import java.util.List;

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
