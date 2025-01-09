package vn.edu.usth.mcma.frontend.ConnectAPI.Retrofit.APIs;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import vn.edu.usth.mcma.frontend.ConnectAPI.Model.Response.SeatTypeResponse;
import vn.edu.usth.mcma.frontend.ConnectAPI.Model.Response.Seat;

public interface SeatAPI {
    @GET("/api/v1/user/booking/seat-types")
    Call<List<SeatTypeResponse>> getAllSeatTypes();
    @GET("/api/v1/user/booking/seat/{screenId}")
    Call<List<Seat>> getAllSeatsByScreenId(@Path("screenId") Long screenId);
}
