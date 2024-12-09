package vn.edu.usth.mcma.frontend.ConnectAPI.Retrofit.APIs;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import vn.edu.usth.mcma.frontend.ConnectAPI.Model.Response.SearchMovieByNameResponse;

public interface SearchMovieByName {
    @GET("/api/v1/user/search-movie-by-name")
    Call<List<SearchMovieByNameResponse>> searchMovies(
            @Query("title") String title,
            @Query("limit") int limit,
            @Query("offset") int offset
    );
}
