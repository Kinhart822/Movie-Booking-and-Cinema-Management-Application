package vn.edu.usth.mcma.frontend.network.apis;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import vn.edu.usth.mcma.frontend.dto.Response.SearchMovieByGenreResponse;

public interface SearchMovieByGenre {
    @GET("/api/v1/user/view/highRatingMovies")//todo: correct
    Call<List<SearchMovieByGenreResponse>> getAllMoviesByGenre(@Query("movieGenreId") Integer movieGenreId);;
}
