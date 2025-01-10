package vn.edu.usth.mcma.frontend.network.apis;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import vn.edu.usth.mcma.frontend.dto.Response.MovieResponse;

public interface GetAllInformationOfSelectedMovie {
    @GET("/api/v1/user/information/movie-information/{movieId}")
    Call<MovieResponse> getAllInformationOfSelectedMovie(@Path("movieId") long movieId);
}
