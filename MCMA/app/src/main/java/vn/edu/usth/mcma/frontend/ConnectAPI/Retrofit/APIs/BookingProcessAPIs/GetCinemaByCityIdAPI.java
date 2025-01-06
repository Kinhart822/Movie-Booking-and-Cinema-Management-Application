package vn.edu.usth.mcma.frontend.ConnectAPI.Retrofit.APIs.BookingProcessAPIs;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import vn.edu.usth.mcma.frontend.ConnectAPI.Model.Response.BookingProcess.CinemaResponse;

public interface GetCinemaByCityIdAPI {
    @GET("/api/v1/user/booking/allCinemasByCity/{cityId}")
    Call<List<CinemaResponse>> getCinemasByCityId(@Path("cityId") int cityId);

    @GET("/api/v1/user/booking/allCinemasByMovieAndCity")
    Call<List<CinemaResponse>> getCinemasByMovieIdAndCityId(
            @Query("movieId") Long movieId,
            @Query("cityId") Long cityId
    );
}
