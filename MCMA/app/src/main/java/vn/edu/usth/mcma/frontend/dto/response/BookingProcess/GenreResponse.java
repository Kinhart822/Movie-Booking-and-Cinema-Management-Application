package vn.edu.usth.mcma.frontend.dto.response.BookingProcess;

import lombok.Data;

@Data
public class GenreResponse {
    private Long id;
    private String name;
    private String description;
    private String imageBase64;
}
