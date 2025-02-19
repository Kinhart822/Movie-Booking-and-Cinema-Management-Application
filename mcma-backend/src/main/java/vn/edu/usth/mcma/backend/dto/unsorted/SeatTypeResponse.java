package vn.edu.usth.mcma.backend.dto.unsorted;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SeatTypeResponse {
    private String id;
    private String description;
    private Integer width;
    private Integer height;
    private Double unitPrice;
}
