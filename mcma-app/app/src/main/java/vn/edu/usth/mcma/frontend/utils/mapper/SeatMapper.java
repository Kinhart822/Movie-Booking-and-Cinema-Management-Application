package vn.edu.usth.mcma.frontend.utils.mapper;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import vn.edu.usth.mcma.frontend.model.response.SeatTypeResponse;
import vn.edu.usth.mcma.frontend.model.parcelable.SeatParcelable;
import vn.edu.usth.mcma.frontend.model.item.SeatItem;
import vn.edu.usth.mcma.frontend.model.response.SeatResponse;

public class SeatMapper {
    public static SeatItem fromResponse(SeatResponse response) {
        return SeatItem.builder()
                .row(response.getRow())
                .col(response.getCol())
                .typeId(response.getTypeId())
                .name(response.getName())
                .rootRow(response.getRootRow())
                .rootCol(response.getRootCol())
                .availability(response.getAvailability()).build();
    }
    public static List<SeatItem> fromResponseList(List<SeatResponse> responses) {
        return responses.stream()
                .map(SeatMapper::fromResponse)
                .toList();
    }
    public static SeatParcelable fromItem(SeatItem item) {
        return SeatParcelable.builder()
                .row(item.getRow())
                .col(item.getCol())
                .name(item.getName())
                .typeId(item.getTypeId())
                .rootRow(item.getRootRow())
                .rootCol(item.getRootCol()).build();
    }
    public static List<SeatParcelable> fromItemList(List<SeatItem> items) {
        return items.stream()
                .map(SeatMapper::fromItem)
                .toList();
    }
}
