package vn.edu.usth.mcma.frontend.ConnectAPI.Retrofit.APIs.BookingProcessAPIs;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import vn.edu.usth.mcma.frontend.ConnectAPI.Model.Request.BookingRequest;
import vn.edu.usth.mcma.frontend.ConnectAPI.Model.Response.BookingProcess.SendBookingResponse;
import vn.edu.usth.mcma.frontend.ConnectAPI.Model.Response.BookingResponse;

public interface BookingAPI {
    @POST("/api/v1/user/booking/processingBooking")
    Call<SendBookingResponse> processBooking(@Body BookingRequest bookingRequest);

    @POST("/api/v1/user/booking/completeBooking")
    Call<BookingResponse> completeBooking(@Body BookingRequest bookingRequest);

    @GET("/api/v1/user/view/getAllCompletedBookingsThatHaveStartDateTimeHigherThanNowByUser")
    Call<List<BookingResponse>> getAllCompletedBookingsByUser();

    @POST("/api/v1/user/booking/cancel-booking/{bookingId}")
    Call<Void> cancelBooking(@Path("bookingId") int bookingId);

    @GET("/api/v1/user/view/allBookingsCanceledByUser")
    Call<List<BookingResponse>> getAllBookingsCanceled();

    @POST("/api/v1/user/booking/revoke-cancel-booking/{bookingId}")
    Call<Void> revokeCancelBooking(@Path("bookingId") int bookingId);

    @GET("/api/v1/user/view/getAllBookingsThatHaveStartDateTimeHigherThanNowByUser")
    Call<List<BookingResponse>> getAllBookingsByUser();

    @DELETE("/api/v1/user/booking/delete-booking/{bookingId}")
    Call<Void> deleteBooking(@Path("bookingId") int bookingId);
}
