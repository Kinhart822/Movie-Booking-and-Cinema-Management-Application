package vn.edu.usth.mcma.frontend.model.response;

import lombok.Data;

@Data
public class NotificationResponse {
    private Long id;
    private String title;
    private String content;
    private String createdDate;
}
