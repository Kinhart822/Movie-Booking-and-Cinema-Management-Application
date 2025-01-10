package vn.edu.usth.mcma.frontend.network.apis;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import vn.edu.usth.mcma.frontend.dto.Response.MovieRespondResponse;

public interface GetAllFeedBackAPI {
    @GET("/api/v1/user/view/viewMovieRespondByUser")
    Call<List<MovieRespondResponse>> getAllUserFeedback();

    @GET("/api/v1/user/view/viewMovieRespondByMovie/{movieId}")
    Call<List<MovieRespondResponse>> viewMovieRespondByMovie(@Path("movieId") int movieId);
}
