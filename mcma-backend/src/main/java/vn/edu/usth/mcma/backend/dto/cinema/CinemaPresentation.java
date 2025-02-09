package vn.edu.usth.mcma.backend.dto.cinema;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CinemaPresentation {
    private Long id;
    private String name;
    private String address;
}
