package vn.edu.usth.mcma.frontend.ConnectAPI.Retrofit.APIs;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import vn.edu.usth.mcma.frontend.ConnectAPI.Model.Request.MovieRespondRequest;


public interface AddMovieRespondAPI {
    @POST("/api/v1/user/movieRespond/add")
    Call<MovieRespondRequest> addRespond(@Body MovieRespondRequest movieRespondRequest);
}
