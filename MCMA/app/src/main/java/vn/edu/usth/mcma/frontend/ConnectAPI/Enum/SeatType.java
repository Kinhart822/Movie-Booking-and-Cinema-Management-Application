package vn.edu.usth.mcma.frontend.ConnectAPI.Enum;

import lombok.Data;

@Data
public class SeatType {
    private Integer id;
    private String name;
    private String description;
    private Integer width;
    private Integer length;
    private Double price;
}
