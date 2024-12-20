package vn.edu.usth.mcma.backend.dto;

import lombok.Data;

@Data
public class ScreenRequest {
    private Long cinemaId;
    private String name;
    private Integer typeId;
}
