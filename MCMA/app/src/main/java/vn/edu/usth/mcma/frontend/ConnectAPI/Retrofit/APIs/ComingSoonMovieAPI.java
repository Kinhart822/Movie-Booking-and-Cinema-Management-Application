package vn.edu.usth.mcma.frontend.ConnectAPI.Retrofit.APIs;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import vn.edu.usth.mcma.frontend.ConnectAPI.Model.Response.ComingSoonResponse;
import vn.edu.usth.mcma.frontend.ConnectAPI.Model.Response.NowShowingResponse;

public interface ComingSoonMovieAPI {
    @GET("/api/v1/user/view/comingSoonMovies")
    Call<List<ComingSoonResponse>> getAvailableComingSoonMovies();
}
