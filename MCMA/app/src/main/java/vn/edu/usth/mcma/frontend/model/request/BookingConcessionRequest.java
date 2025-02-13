package vn.edu.usth.mcma.frontend.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookingConcessionRequest {
    private Long id;
    private int quantity;
}
