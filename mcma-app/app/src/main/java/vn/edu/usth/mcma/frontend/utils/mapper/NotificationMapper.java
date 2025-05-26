package vn.edu.usth.mcma.frontend.utils.mapper;

import java.util.List;

import vn.edu.usth.mcma.frontend.model.item.NotificationItem;
import vn.edu.usth.mcma.frontend.model.response.NotificationResponse;

public class NotificationMapper {
    public static NotificationItem fromResponse(NotificationResponse response) {
        return NotificationItem.builder()
                .id(response.getId())
                .title(response.getTitle())
                .content(response.getContent())
                .createdDate(response.getCreatedDate())
                .build();
    }
    public static List<NotificationItem> fromResponseList(List<NotificationResponse> response) {
        return response.stream().map(NotificationMapper::fromResponse).toList();
    }
}
