package vn.edu.usth.mcma.frontend.ConnectAPI.Retrofit.APIs.BookingProcessAPIs;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import vn.edu.usth.mcma.frontend.ConnectAPI.Model.Request.BookingRequest;
import vn.edu.usth.mcma.frontend.ConnectAPI.Model.Response.BookingProcess.SendBookingResponse;

public interface BookingAPI {
    @POST("/api/v1/user/booking/processingBooking")
    Call<SendBookingResponse> processBooking(@Body BookingRequest bookingRequest);
}
