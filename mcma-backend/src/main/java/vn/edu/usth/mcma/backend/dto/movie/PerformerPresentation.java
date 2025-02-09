package vn.edu.usth.mcma.backend.dto.movie;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PerformerPresentation {
    private Long id;
    private String name;
    private Integer typeId;
    private Instant dob;
    private Integer sex;
}
