package vn.edu.usth.mcma.frontend.network.apis;

import retrofit2.Call;
import retrofit2.http.GET;
import vn.edu.usth.mcma.frontend.dto.Response.NotificationResponse;

public interface GetNotifications {
    @GET("/api/v1/user/view/notifications")
    Call<NotificationResponse> getNotifications();
}
