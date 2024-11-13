package vn.edu.usth.mcma.service.admin.service.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieGenreDetailRequestDTO {
    private String name;
    private String description;
}
