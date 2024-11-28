package vn.edu.usth.mcma.frontend.ConnectAPI.Retrofit.APIs;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import vn.edu.usth.mcma.frontend.ConnectAPI.Model.Request.UpdateAccountRequest;

public interface EditAccountAPI {
    @PUT("/api/v1/auth/update-account/{userId}")
    Call<Void> updateAccount(@Path("userId") int userId, @Body UpdateAccountRequest updateAccountRequest);
}
