package vn.edu.usth.mcma.frontend.ConnectAPI.Retrofit.APIs;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import vn.edu.usth.mcma.frontend.ConnectAPI.Enum.SeatType;

public interface HelperAPI {
    @GET("/api/v1/user/seat-types")
    Call<List<SeatType>> getAllSeatTypes();
}
