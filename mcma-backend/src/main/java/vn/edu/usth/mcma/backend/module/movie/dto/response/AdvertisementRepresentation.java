package vn.edu.usth.mcma.backend.module.movie.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class AdvertisementRepresentation {
    private Long id;
    private String banner;
}
