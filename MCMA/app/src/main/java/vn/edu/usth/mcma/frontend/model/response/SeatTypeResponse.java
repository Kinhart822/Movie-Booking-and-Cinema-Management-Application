package vn.edu.usth.mcma.frontend.model.response;

import lombok.Data;

@Data
public class SeatTypeResponse {
    private String id;
    private String description;
    private Integer width;
    private Integer height;
    private Double unitPrice;
}
