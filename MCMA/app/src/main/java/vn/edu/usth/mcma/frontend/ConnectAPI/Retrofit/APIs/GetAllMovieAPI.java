package vn.edu.usth.mcma.frontend.ConnectAPI.Retrofit.APIs;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import vn.edu.usth.mcma.frontend.ConnectAPI.Model.Response.ComingSoonResponse;
import vn.edu.usth.mcma.frontend.ConnectAPI.Model.Response.MovieResponse;

public interface GetAllMovieAPI {
    @GET("/api/v1/user/information/allMovies")
    Call<List<MovieResponse>> getAllMovie();
}
