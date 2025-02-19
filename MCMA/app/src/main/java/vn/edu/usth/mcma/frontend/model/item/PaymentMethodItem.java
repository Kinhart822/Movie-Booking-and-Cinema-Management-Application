package vn.edu.usth.mcma.frontend.model.item;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentMethodItem {
    private String id;
    private String description;
    private String appPackageName;
    private String imageBase64;
}
