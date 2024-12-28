package vn.edu.usth.mcma.frontend.ConnectAPI.Retrofit.APIs.BookingProcessAPIs;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import vn.edu.usth.mcma.frontend.ConnectAPI.Model.Response.BookingProcess.CinemaResponse;
import vn.edu.usth.mcma.frontend.ConnectAPI.Model.Response.BookingProcess.Seat.AvailableSeatResponse;

public interface GetAllAvailableSeatsByScreenAPI {
    @GET("/api/v1/user/booking/allAvailableSeatsByScreen/{screenId}")
    Call<List<AvailableSeatResponse>> getAvailableSeatsByScreen(@Path("screenId") int screenId);

}

