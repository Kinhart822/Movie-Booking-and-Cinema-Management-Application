package vn.edu.usth.mcma.backend.dto;

import lombok.Data;

@Data
public class DrinkRequest {
    private String name;
    private String description;
    private String imageUrl;
    private String size;
    private Integer volume;
    private Integer price;
}
