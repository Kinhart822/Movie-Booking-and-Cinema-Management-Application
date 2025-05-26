package vn.edu.usth.mcma.frontend.utils.mapper;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import vn.edu.usth.mcma.frontend.model.parcelable.SeatTypeParcelable;
import vn.edu.usth.mcma.frontend.model.response.SeatTypeItem;
import vn.edu.usth.mcma.frontend.model.response.SeatTypeResponse;

public class SeatTypeMapper {
    public static SeatTypeItem fromResponse(SeatTypeResponse response) {
        return SeatTypeItem.builder()
                .id(response.getId())
                .description(response.getDescription())
                .width(response.getWidth())
                .height(response.getHeight())
                .unitPrice(response.getUnitPrice())
                .build();
    }
    public static Map<String, SeatTypeItem> fromResponseList(List<SeatTypeResponse> responses) {
        Map<String, SeatTypeItem> seatTypeIdSeatTypeMap = new TreeMap<>();
        responses.forEach(st -> seatTypeIdSeatTypeMap.put(st.getId(), fromResponse(st)));
        return seatTypeIdSeatTypeMap;
    }
    public static SeatTypeParcelable fromItem(SeatTypeItem item) {
        return SeatTypeParcelable.builder()
                .id(item.getId())
                .description(item.getDescription())
                .width(item.getWidth())
                .height(item.getHeight())
                .unitPrice(item.getUnitPrice())
                .build();
    }
    public static Map<String, SeatTypeParcelable> fromItemMap(Map<String, SeatTypeItem> seatTypes) {
        Map<String, SeatTypeParcelable> seatTypeIdSeatTypeMap = new TreeMap<>();
        seatTypes.forEach((typeId, seatType) -> {
            if (seatType.getUnitPrice() == null) {
                return;
            }
            seatTypeIdSeatTypeMap.put(typeId, fromItem(seatType));
        });
        return seatTypeIdSeatTypeMap;
    }
}
