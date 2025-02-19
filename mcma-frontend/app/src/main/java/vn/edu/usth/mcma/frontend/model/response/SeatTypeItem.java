package vn.edu.usth.mcma.frontend.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SeatTypeItem {
    private String id;
    private String description;
    private Integer width;
    private Integer height;
    private Double unitPrice;
}
