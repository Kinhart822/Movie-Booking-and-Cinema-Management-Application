package vn.edu.usth.mcma.frontend.ConnectAPI.Retrofit.APIs;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import vn.edu.usth.mcma.frontend.ConnectAPI.Model.Response.BookingResponse;
import vn.edu.usth.mcma.frontend.ConnectAPI.Model.Response.MovieRespondResponse;

public interface GetAllFeedBackAPI {
    @GET("/api/v1/user/view/viewMovieRespondByUser")
    Call<List<MovieRespondResponse>> getAllUserFeedback();
}
