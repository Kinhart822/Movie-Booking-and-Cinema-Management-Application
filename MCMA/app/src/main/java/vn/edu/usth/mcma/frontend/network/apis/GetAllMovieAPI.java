package vn.edu.usth.mcma.frontend.network.apis;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import vn.edu.usth.mcma.frontend.dto.Response.MovieResponse;

public interface GetAllMovieAPI {
    @GET("/api/v1/user/information/allMovies")
    Call<List<MovieResponse>> getAllMovie();

    @GET("/api/v1/user/view/getAllMovieInformationBySelectedDateSchedule")
    Call<List<MovieResponse>> getAllMovieBySelectedDate(@Query("date") String date);
}
