package vn.edu.usth.mcma.frontend.ConnectAPI.Retrofit.APIs.BookingProcessAPIs;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import vn.edu.usth.mcma.frontend.ConnectAPI.Model.Response.BookingProcess.CinemaResponse;
import vn.edu.usth.mcma.frontend.ConnectAPI.Model.Response.BookingProcess.CityResponse;

public interface GetCinemaByCityIdAPI {
    @GET("/api/v1/user/booking/allCinemasByCity/{cityId}")
    Call<List<CinemaResponse>> getCinemasByCityId(@Path("cityId") int cityId);
}
