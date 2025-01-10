package vn.edu.usth.mcma.frontend.network.apis.BookingProcessAPIs;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import vn.edu.usth.mcma.frontend.dto.Response.BookingProcess.Seat.UnavailableSeatResponse;

public interface GetAllUnavailableSeatsByScreenAPI {
    @GET("/api/v1/user/booking/allUnavailableSeatsByScreen/{screenId}")
    Call<List<UnavailableSeatResponse>> getUnavailableSeatsByScreen(@Path("screenId") Long screenId);
}
