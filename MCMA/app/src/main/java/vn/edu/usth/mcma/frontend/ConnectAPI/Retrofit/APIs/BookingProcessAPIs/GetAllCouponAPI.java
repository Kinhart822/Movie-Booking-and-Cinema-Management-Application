package vn.edu.usth.mcma.frontend.ConnectAPI.Retrofit.APIs.BookingProcessAPIs;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import vn.edu.usth.mcma.frontend.ConnectAPI.Model.Response.BookingProcess.CouponResponse;

public interface GetAllCouponAPI {
    @GET("/api/v1/user/booking/allCouponsByUser")
    Call<List<CouponResponse>> getAllCouponByUser();

    @GET("/api/v1/user/booking/allCouponsByMovie/{movieId}")
    Call<List<CouponResponse>> getAllCouponsByMovie(@Path("movieId") int movieId);
}
