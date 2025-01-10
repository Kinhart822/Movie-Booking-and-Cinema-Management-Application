package vn.edu.usth.mcma.frontend.dto.Response;

import lombok.Data;

@Data
public class SeatTypeResponse {
    private Integer id;
    private String name;
    private String description;
    private Integer width;
    private Integer length;
    private Double price;
}
