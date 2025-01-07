package vn.edu.usth.mcma.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SeatTypePresentation {
    private Integer id;
    private String name;
    private String description;
    private Integer width;
    private Integer length;
    private Double price;
}
