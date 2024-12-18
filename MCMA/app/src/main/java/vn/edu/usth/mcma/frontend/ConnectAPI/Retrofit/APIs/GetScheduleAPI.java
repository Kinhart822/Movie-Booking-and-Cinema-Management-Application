package vn.edu.usth.mcma.frontend.ConnectAPI.Retrofit.APIs;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import vn.edu.usth.mcma.frontend.ConnectAPI.Model.Response.ScheduleResponse;
import vn.edu.usth.mcma.frontend.ConnectAPI.Model.Response.ViewCinemaResponse;

public interface GetScheduleAPI {
    @GET("/api/v1/user/view/allScheduleByCinema/{cinemaId}")
    Call<List<ScheduleResponse>> getScheduleByCinema(@Path("cinemaId") int cinemaId);


}
