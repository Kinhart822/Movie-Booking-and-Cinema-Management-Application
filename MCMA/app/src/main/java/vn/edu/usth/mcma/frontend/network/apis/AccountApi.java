package vn.edu.usth.mcma.frontend.network.apis;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import vn.edu.usth.mcma.frontend.dto.Request.ChangePasswordRequest;
import vn.edu.usth.mcma.frontend.dto.Request.UpdateAccountRequest;
import vn.edu.usth.mcma.frontend.dto.Response.BookingProcess.CouponResponse;
import vn.edu.usth.mcma.frontend.dto.Response.BookingResponse;
import vn.edu.usth.mcma.frontend.dto.Response.MovieRespondResponse;
import vn.edu.usth.mcma.frontend.dto.Response.NotificationResponse;
import vn.edu.usth.mcma.frontend.dto.Response.UserDetailsResponse;

public interface AccountApi {
    @PUT("/api/v1/auth/update-account/{userId}")
    Call<Void> updateAccount(@Path("userId") int userId, @Body UpdateAccountRequest updateAccountRequest);
    @DELETE("/api/v1/auth/delete-account/{userId}")
    Call<Void> deleteAccount(@Path("userId") int userId);
    @GET("/api/v1/user/view/allBookingsByUser")
    Call<List<BookingResponse>> getBookingHistory();
    @GET("/api/v1/user/view/viewMovieRespondByUser")
    Call<List<MovieRespondResponse>> getAllUserFeedback();
    @GET("/api/v1/user/view/notifications")
    Call<NotificationResponse> getNotifications();
    @POST("/api/v1/auth/update-password")
    Call<String> updatePassword(@Body ChangePasswordRequest changePasswordRequest);
    @GET("api/v1/auth/getUserInformation")
    Call<UserDetailsResponse> getUserDetailByUser();
    @GET("/api/v1/user/booking/allCouponsByUser")
    Call<List<CouponResponse>> getAllCouponByUser();
}
