package vn.edu.usth.mcma.backend.dto;

import lombok.Data;

@Data
public class ScreenRequest {
    private String name;
    private Long typeId;
    private Integer status;
}
