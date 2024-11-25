package vn.edu.usth.mcma.frontend.ConnectAPI.Retrofit.APIs;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import vn.edu.usth.mcma.frontend.ConnectAPI.Model.Request.ChangePasswordRequest;
import vn.edu.usth.mcma.frontend.ConnectAPI.Model.Response.JwtAuthenticationResponse;

public interface UpdatePasswordAPI {
    @POST("/api/v1/auth/update-password")
    Call<String> updatePassword(@Body ChangePasswordRequest changePasswordRequest);
}
