package vn.edu.usth.mcma.backend.module.cinema.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FilterScreen {
    private Long screenId;
    private String screenName;
}
