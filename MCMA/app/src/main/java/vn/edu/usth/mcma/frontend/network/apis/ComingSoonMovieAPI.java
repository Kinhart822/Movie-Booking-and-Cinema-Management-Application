package vn.edu.usth.mcma.frontend.network.apis;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import vn.edu.usth.mcma.frontend.dto.Response.ComingSoonResponse;

public interface ComingSoonMovieAPI {
    @GET("/api/v1/user/view/comingSoonMovies")
    Call<List<ComingSoonResponse>> getAvailableComingSoonMovies();
}
