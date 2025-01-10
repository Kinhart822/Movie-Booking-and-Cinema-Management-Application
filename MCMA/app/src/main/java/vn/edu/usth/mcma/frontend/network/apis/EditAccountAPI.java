package vn.edu.usth.mcma.frontend.network.apis;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import vn.edu.usth.mcma.frontend.dto.Request.UpdateAccountRequest;

public interface EditAccountAPI {
    @PUT("/api/v1/auth/update-account/{userId}")
    Call<Void> updateAccount(@Path("userId") int userId, @Body UpdateAccountRequest updateAccountRequest);
}
