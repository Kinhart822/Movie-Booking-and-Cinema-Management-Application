package vn.edu.usth.mcma.frontend.ConnectAPI.Retrofit.APIs.BookingProcessAPIs;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import vn.edu.usth.mcma.frontend.ConnectAPI.Model.Response.BookingProcess.ScheduleResponse;

public interface GetDateScheduleByScreenIdAPI {
    @GET("/api/v1/user/booking/allSchedulesByMovieAndCinemaAndScreen")
    Call<ScheduleResponse> getAllSchedulesByMovieAndCinemaAndScreen(
            @Query("movieId") Integer movieId,
            @Query("cinemaId") Integer cinemaId,
            @Query("screenId") Integer screenId
    );

}
