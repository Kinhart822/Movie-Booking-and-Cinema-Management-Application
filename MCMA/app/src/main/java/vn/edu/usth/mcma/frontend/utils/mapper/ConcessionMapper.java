package vn.edu.usth.mcma.frontend.utils.mapper;

import java.util.List;
import java.util.stream.Collectors;

import vn.edu.usth.mcma.frontend.model.item.ConcessionItem;
import vn.edu.usth.mcma.frontend.model.parcelable.ConcessionParcelable;
import vn.edu.usth.mcma.frontend.model.response.ConcessionResponse;

public class ConcessionMapper {
    public static ConcessionItem fromResponse(ConcessionResponse response) {
        return ConcessionItem.builder()
                .id(response.getId())
                .name(response.getName())
                .description(response.getDescription())
                .comboPrice(response.getComboPrice())
                .imageBase64(response.getImageBase64())
                .quantity(0)
                .build();
    }
    public static List<ConcessionItem> fromResponseList(List<ConcessionResponse> responses) {
        return responses
                .stream()
                .map(ConcessionMapper::fromResponse)
                .collect(Collectors.toList());
    }
    public static ConcessionParcelable fromItem(ConcessionItem item) {
        return ConcessionParcelable.builder()
                .id(item.getId())
                .comboPrice(item.getComboPrice())
                .quantity(item.getQuantity())
                .build();
    }
    public static List<ConcessionParcelable> fromItemList(List<ConcessionItem> items) {
        return items
                .stream()
                .map(ConcessionMapper::fromItem)
                .collect(Collectors.toList());
    }
}
