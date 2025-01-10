package vn.edu.usth.mcma.frontend.network.apis;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import vn.edu.usth.mcma.frontend.dto.Request.ChangePasswordRequest;

public interface UpdatePasswordAPI {
    @POST("/api/v1/auth/update-password")
    Call<String> updatePassword(@Body ChangePasswordRequest changePasswordRequest);
}
