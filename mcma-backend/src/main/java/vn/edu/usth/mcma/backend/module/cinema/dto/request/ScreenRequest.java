package vn.edu.usth.mcma.backend.module.cinema.dto.request;

import lombok.Data;

@Data
public class ScreenRequest {
    private String name;
    private String typeId;
    private Integer status;
}
