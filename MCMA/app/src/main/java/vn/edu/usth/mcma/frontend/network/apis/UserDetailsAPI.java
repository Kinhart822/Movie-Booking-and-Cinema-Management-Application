package vn.edu.usth.mcma.frontend.network.apis;

import retrofit2.Call;
import retrofit2.http.GET;
import vn.edu.usth.mcma.frontend.dto.Response.UserDetailsResponse;

public interface UserDetailsAPI {
    @GET("api/v1/auth/getUserInformation")
    Call<UserDetailsResponse> getUserDetailByUser();

}
