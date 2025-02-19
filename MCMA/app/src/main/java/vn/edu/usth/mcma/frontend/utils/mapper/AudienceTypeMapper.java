package vn.edu.usth.mcma.frontend.utils.mapper;

import java.util.List;

import vn.edu.usth.mcma.frontend.model.item.AudienceTypeItem;
import vn.edu.usth.mcma.frontend.model.parcelable.AudienceTypeParcelable;
import vn.edu.usth.mcma.frontend.model.response.AudienceTypeResponse;

public class AudienceTypeMapper {
    public static AudienceTypeItem fromResponse(AudienceTypeResponse response) {
        return AudienceTypeItem.builder()
                .id(response.getId())
                .unitPrice(response.getUnitPrice())
                .quantity(0).build();
    }
    public static List<AudienceTypeItem> fromResponseList(List<AudienceTypeResponse> responses) {
        return responses.stream()
                .map(AudienceTypeMapper::fromResponse)
                .toList();
    }
    public static AudienceTypeParcelable fromItem(AudienceTypeItem item) {
        return AudienceTypeParcelable.builder()
                .id(item.getId())
                .unitPrice(item.getUnitPrice())
                .quantity(item.getQuantity()).build();
    }
    public static List<AudienceTypeParcelable> fromItemList(List<AudienceTypeItem> items) {
        return items.stream()
                .map(AudienceTypeMapper::fromItem)
                .toList();
    }
}
