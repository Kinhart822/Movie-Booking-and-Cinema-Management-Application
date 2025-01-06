package vn.edu.usth.mcma.frontend.ConnectAPI.Retrofit.APIs;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import vn.edu.usth.mcma.frontend.ConnectAPI.Model.Response.MovieResponse;

public interface GetAllInformationOfSelectedMovie {
    @GET("/api/v1/user/information/movie-information/{movieId}")
    Call<MovieResponse> getAllInformationOfSelectedMovie(@Path("movieId") long movieId);
}
