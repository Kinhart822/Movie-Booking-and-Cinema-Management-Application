package vn.edu.usth.mcma.backend.module.cinema.dto.request;

import lombok.Data;

@Data
public class CinemaRequest {
    private Long cityId;
    private String name;
    private String address;
    private Integer status;
}
