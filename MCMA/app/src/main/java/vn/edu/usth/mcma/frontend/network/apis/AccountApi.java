package vn.edu.usth.mcma.frontend.network.apis;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import vn.edu.usth.mcma.frontend.dto.request.ChangePasswordRequest;
import vn.edu.usth.mcma.frontend.dto.request.UpdateAccountRequest;
import vn.edu.usth.mcma.frontend.dto.request.account.SignUpFinish;
import vn.edu.usth.mcma.frontend.dto.request.account.SignUpCheckOtp;
import vn.edu.usth.mcma.frontend.dto.request.account.SignUpBegin;
import vn.edu.usth.mcma.frontend.dto.response.ApiResponse;
import vn.edu.usth.mcma.frontend.dto.response.BookingProcess.CouponResponse;
import vn.edu.usth.mcma.frontend.dto.response.BookingResponse;
import vn.edu.usth.mcma.frontend.dto.response.account.EmailExistenceResponse;
import vn.edu.usth.mcma.frontend.dto.response.MovieRespondResponse;
import vn.edu.usth.mcma.frontend.dto.response.NotificationResponse;
import vn.edu.usth.mcma.frontend.dto.response.UserDetailsResponse;
import vn.edu.usth.mcma.frontend.dto.response.account.VerifyEmailDueDate;
import vn.edu.usth.mcma.frontend.dto.response.account.VerifyEmailOtpCheckResult;

public interface AccountApi {
    @GET("/api/v1/account/user/sign-up/check-email-existence")
    Call<EmailExistenceResponse> checkEmailExistence(@Query("query") String query);
    @POST("/api/v1/account/user/sign-up/begin")
    Call<VerifyEmailDueDate> signUpBegin(@Body SignUpBegin request);
    @POST("/api/v1/account/user/sign-up/check-otp")
    Call<VerifyEmailOtpCheckResult> signUpCheckOtp(@Body SignUpCheckOtp check);
    @POST("/api/v1/account/user/sign-up/finish")
    Call<ApiResponse> signUpFinish(@Body SignUpFinish signUpFinish);

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
