package vn.edu.usth.mcma.frontend.utils.mapper;

import java.util.List;

import vn.edu.usth.mcma.frontend.model.item.PaymentMethodItem;
import vn.edu.usth.mcma.frontend.model.response.PaymentMethodResponse;

public class PaymentMethodMapper {
    public static PaymentMethodItem fromResponse(PaymentMethodResponse response) {
        return PaymentMethodItem.builder()
                .id(response.getId())
                .description(response.getDescription())
                .appPackageName(response.getAppPackageName())
                .imageBase64(response.getImageBase64()).build();
    }
    public static List<PaymentMethodItem> fromResponseList(List<PaymentMethodResponse> responses) {
        return responses.stream()
                .map(PaymentMethodMapper::fromResponse)
                .toList();
    }
}
