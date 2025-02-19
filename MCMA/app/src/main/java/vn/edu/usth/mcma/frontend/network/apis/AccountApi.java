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
import vn.edu.usth.mcma.frontend.dto.account.NewPassword;
import vn.edu.usth.mcma.frontend.dto.request.ChangePasswordRequest;
import vn.edu.usth.mcma.frontend.dto.request.UpdateAccountRequest;
import vn.edu.usth.mcma.frontend.dto.account.SignUpRequest;
import vn.edu.usth.mcma.frontend.dto.account.CheckOtpRequest;
import vn.edu.usth.mcma.frontend.dto.account.SendOtpRequest;
import vn.edu.usth.mcma.frontend.dto.response.ApiResponse;
import vn.edu.usth.mcma.frontend.dto.response.BookingProcess.CouponResponse;
import vn.edu.usth.mcma.frontend.dto.response.BookingResponse;
import vn.edu.usth.mcma.frontend.dto.account.EmailExistenceResponse;
import vn.edu.usth.mcma.frontend.dto.response.MovieRespondResponse;
import vn.edu.usth.mcma.frontend.model.response.NotificationResponse;
import vn.edu.usth.mcma.frontend.dto.response.UserDetailsResponse;
import vn.edu.usth.mcma.frontend.dto.account.OtpDueDate;
import vn.edu.usth.mcma.frontend.dto.account.OtpCheckResult;

public interface AccountApi {
    @GET("/api/v1/account/user/sign-up/check-email-existence")
    Call<EmailExistenceResponse> checkEmailExistence(@Query("query") String query);
    @POST("/api/v1/account/user/sign-up/begin")
    Call<OtpDueDate> signUpBegin(@Body SendOtpRequest request);
    @POST("/api/v1/account/user/sign-up/check-otp")
    Call<OtpCheckResult> signUpCheckOtp(@Body CheckOtpRequest request);
    @POST("/api/v1/account/user/sign-up/finish")
    Call<ApiResponse> signUpFinish(@Body SignUpRequest request);

    @POST("/api/v1/account/user/forgot-password/begin")
    Call<OtpDueDate> forgotPasswordBegin(@Body SendOtpRequest request);
    @POST("/api/v1/account/user/forgot-password/check-otp")
    Call<OtpCheckResult> forgotPasswordCheckOtp(@Body CheckOtpRequest request);
    @POST("/api/v1/account/user/forgot-password/finish")
    Call<ApiResponse> forgotPasswordFinish(@Body NewPassword request);

    @GET("/api/v1/account/user/notification")
    Call<List<NotificationResponse>> findAllNotifications();

    @PUT("/api/v1/auth/update-account/{userId}")
    Call<Void> updateAccount(@Path("userId") int userId, @Body UpdateAccountRequest updateAccountRequest);
    @DELETE("/api/v1/auth/delete-account/{userId}")
    Call<Void> deleteAccount(@Path("userId") int userId);
    @GET("/api/v1/user/view/allBookingsByUser")
    Call<List<BookingResponse>> getBookingHistory();
    @GET("/api/v1/user/view/viewMovieRespondByUser")
    Call<List<MovieRespondResponse>> getAllUserFeedback();
    @POST("/api/v1/auth/update-password")
    Call<String> updatePassword(@Body ChangePasswordRequest changePasswordRequest);
    @GET("api/v1/auth/getUserInformation")
    Call<UserDetailsResponse> getUserDetailByUser();
    @GET("/api/v1/user/booking/allCouponsByUser")
    Call<List<CouponResponse>> getAllCouponByUser();
}
