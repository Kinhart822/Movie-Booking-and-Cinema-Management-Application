package vn.edu.usth.mcma.frontend.ConnectAPI.Retrofit.APIs;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import vn.edu.usth.mcma.frontend.ConnectAPI.Model.Response.ScheduleSelectedByCinemaResponse;

public interface GetScheduleAPI {
    @GET("/api/v1/user/view/allScheduleByCinema/{cinemaId}")
    Call<ScheduleSelectedByCinemaResponse> getScheduleByCinema(@Path("cinemaId") int cinemaId);
}
