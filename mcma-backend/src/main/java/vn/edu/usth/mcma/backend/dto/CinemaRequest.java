package vn.edu.usth.mcma.backend.dto;

import lombok.Data;

@Data
public class CinemaRequest {
    private Long cityId;
    private String name;
    private String address;
    private Integer status;
}
