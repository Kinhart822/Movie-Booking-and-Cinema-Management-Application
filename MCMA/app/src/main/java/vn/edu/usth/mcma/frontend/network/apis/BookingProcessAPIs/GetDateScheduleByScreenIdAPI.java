package vn.edu.usth.mcma.frontend.network.apis.BookingProcessAPIs;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import vn.edu.usth.mcma.frontend.dto.Response.Schedule;

public interface GetDateScheduleByScreenIdAPI {
    @GET("/api/v1/user/booking/allSchedulesByMovieAndCinemaAndScreen")
    Call<List<Schedule>> getAllSchedulesByMovieAndScreen(
            @Query("movieId") Long movieId,
            @Query("screenId") Long screenId
    );
}
