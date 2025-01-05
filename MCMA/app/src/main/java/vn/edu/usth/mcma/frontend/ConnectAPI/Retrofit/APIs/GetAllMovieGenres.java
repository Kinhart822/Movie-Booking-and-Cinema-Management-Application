package vn.edu.usth.mcma.frontend.ConnectAPI.Retrofit.APIs;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import vn.edu.usth.mcma.frontend.ConnectAPI.Model.Response.HighRatingMovieResponse;
import vn.edu.usth.mcma.frontend.ConnectAPI.Model.Response.MovieGenreResponse;

public interface GetAllMovieGenres {
    @GET("/api/v1/user/view/allMovieGenres")
    Call<List<MovieGenreResponse>> getAllMovieGenres();
}
