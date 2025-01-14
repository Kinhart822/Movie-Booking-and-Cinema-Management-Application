package vn.edu.usth.mcma.frontend.dto.response;

import java.util.List;

public class NotificationResponse {
    private List<String> message;
    private List<String> dateCreated;

    public List<String> getMessage() {
        return message;
    }

    public void setMessage(List<String> message) {
        this.message = message;
    }

    public List<String> getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(List<String> dateCreated) {
        this.dateCreated = dateCreated;
    }
}
