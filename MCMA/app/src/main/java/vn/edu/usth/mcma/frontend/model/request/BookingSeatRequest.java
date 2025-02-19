package vn.edu.usth.mcma.frontend.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookingSeatRequest {
    private Integer rootRow;
    private Integer rootCol;
}
