package vn.edu.usth.mcma.frontend.ConnectAPI.Retrofit.APIs.BookingProcessAPIs;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import vn.edu.usth.mcma.frontend.ConnectAPI.Model.Response.BookingProcess.ScheduleResponse;

public interface GetDateScheduleByScreenIdAPI {
    @GET("/api/v1/user/booking/allSchedulesByMovieAndCinemaAndScreen")
    Call<ScheduleResponse> getAllSchedulesByMovieAndCinemaAndScreen(
            @Query("movieId") Long movieId,
            @Query("cinemaId") Long cinemaId,
            @Query("screenId") Long screenId
    );

}
