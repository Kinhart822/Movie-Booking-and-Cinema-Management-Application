package vn.edu.usth.mcma.frontend.network.apis;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import vn.edu.usth.mcma.frontend.dto.Response.ViewCinemaResponse;

public interface GetCinemaListAPI {
    @GET("/api/v1/user/view/cinemaList")
    Call<ViewCinemaResponse> getCinemaList();

    @GET("/api/v1/user/view/cinemaListByCity/{cityId}")
    Call<ViewCinemaResponse> getCinemaListByCity(@Path("cityId") int cityId);
}
