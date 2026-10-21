package vn.edu.usth.mcma.backend.dto.request.cinema;

import lombok.Data;

@Data
public class ScreenRequest {
    private String name;
    private String typeId;
    private Integer status;
}
