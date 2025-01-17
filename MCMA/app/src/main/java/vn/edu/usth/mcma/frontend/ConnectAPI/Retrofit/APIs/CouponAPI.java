package vn.edu.usth.mcma.frontend.ConnectAPI.Retrofit.APIs;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import vn.edu.usth.mcma.frontend.ConnectAPI.Model.Response.BookingProcess.CouponResponse;
import vn.edu.usth.mcma.frontend.ConnectAPI.Model.Response.ViewCouponResponse;

public interface CouponAPI {
    @GET("/api/v1/user/view/allCoupons")
    Call<List<CouponResponse>> getAllCoupon();

    @GET("/api/v1/user/view/couponDetails/{couponId}")
    Call<CouponResponse> getCouponDetails(@Path("couponId") int couponId);

    @GET("/api/v1/user/view/couponsByUser")
    Call<ViewCouponResponse> getAllCouponByUser();

    @GET("/api/v1/user/view/viewUserPoints")
    Call<Integer> getUserPoints();

    @POST("/api/v1/user/view/exchangeCoupon/{couponId}")
    Call<CouponResponse> exchangeCoupon(@Path("couponId") int couponId);
}
