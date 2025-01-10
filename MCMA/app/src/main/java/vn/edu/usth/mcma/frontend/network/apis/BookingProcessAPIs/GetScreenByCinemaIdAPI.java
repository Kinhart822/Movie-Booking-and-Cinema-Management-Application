package vn.edu.usth.mcma.frontend.network.apis.BookingProcessAPIs;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import vn.edu.usth.mcma.frontend.dto.Response.BookingProcess.ScreenResponse;

public interface GetScreenByCinemaIdAPI {
    @GET("/api/v1/user/booking/allScreenByCinema/{cinemaId}")
    Call<List<ScreenResponse>> getScreenByCinemaId(@Path("cinemaId") Long cinemaId);
}
