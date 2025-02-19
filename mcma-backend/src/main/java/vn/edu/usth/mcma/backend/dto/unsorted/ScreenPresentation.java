package vn.edu.usth.mcma.backend.dto.unsorted;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ScreenPresentation {
    private Long id;
    private String name;
}
