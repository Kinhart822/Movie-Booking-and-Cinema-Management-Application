package vn.edu.usth.mcma.frontend.ConnectAPI.Retrofit.APIs.BookingProcessAPIs;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import vn.edu.usth.mcma.frontend.ConnectAPI.Model.Response.BookingProcess.Seat.HeldSeatResponse;

public interface GetAllHeldSeatsByScreenAPI {
    @GET("/api/v1/user/booking/allHeldSeatsByScreen/{screenId}")
    Call<List<HeldSeatResponse>> getHeldSeatsByScreen(@Path("screenId") int screenId);
}
