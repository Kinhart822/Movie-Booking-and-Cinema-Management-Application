package vn.edu.usth.mcma.frontend.model.response;

import lombok.Data;

@Data
public class PaymentMethodResponse {
    private String id;
    private String description;
    private String appPackageName;
    private String imageBase64;
}
