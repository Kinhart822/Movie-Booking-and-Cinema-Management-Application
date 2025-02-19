package vn.edu.usth.mcma.frontend.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ConcessionResponse {
    private Long id;
    private String name;
    private String description;
    private Double comboPrice;
    private String imageBase64;
}