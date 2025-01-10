package vn.edu.usth.mcma.frontend.network.apis.BookingProcessAPIs;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import vn.edu.usth.mcma.frontend.dto.Response.BookingProcess.Seat.AvailableSeatResponse;

public interface GetAllAvailableSeatsByScreenAPI {
    @GET("/api/v1/user/booking/allAvailableSeatsByScreen/{screenId}")
    Call<List<AvailableSeatResponse>> getAvailableSeatsByScreen(@Path("screenId") Long screenId);
}

