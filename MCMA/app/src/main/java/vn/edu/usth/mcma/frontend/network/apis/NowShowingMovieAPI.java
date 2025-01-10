package vn.edu.usth.mcma.frontend.network.apis;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import vn.edu.usth.mcma.frontend.dto.Response.NowShowingResponse;

public interface NowShowingMovieAPI {
    @GET("/api/v1/user/view/nowShowingMovies")
    Call<List<NowShowingResponse>> getAvailableNowShowingMovies();
}
