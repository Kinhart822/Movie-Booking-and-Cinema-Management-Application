package vn.edu.usth.mcma.frontend.ConnectAPI.Retrofit.APIs;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import vn.edu.usth.mcma.frontend.ConnectAPI.Model.Response.UserDetailsResponse;

public interface UserDetailsAPI {
    @GET("api/v1/auth/getUserInformation")
    Call<UserDetailsResponse> getUserDetailByUser();

}
