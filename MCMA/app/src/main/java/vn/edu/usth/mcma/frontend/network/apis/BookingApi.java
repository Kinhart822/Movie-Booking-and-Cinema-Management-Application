package vn.edu.usth.mcma.frontend.network.apis;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import vn.edu.usth.mcma.frontend.dto.bookingsession.AudienceDetail;
import vn.edu.usth.mcma.frontend.model.response.ConcessionResponse;
import vn.edu.usth.mcma.frontend.dto.bookingsession.ScheduleDetail;
import vn.edu.usth.mcma.frontend.dto.request.BookingRequest;
import vn.edu.usth.mcma.frontend.dto.response.BookingProcess.CouponResponse;
import vn.edu.usth.mcma.frontend.dto.response.BookingProcess.SendBookingResponse;
import vn.edu.usth.mcma.frontend.dto.response.BookingProcess.TicketResponse;
import vn.edu.usth.mcma.frontend.dto.response.BookingResponse;
import vn.edu.usth.mcma.frontend.dto.response.SeatTypeResponse;
import vn.edu.usth.mcma.frontend.dto.response.ViewCouponResponse;
import vn.edu.usth.mcma.frontend.model.Seat;

public interface BookingApi {
    @GET("/api/v1/user/booking/schedule/{scheduleId}")
    Call<ScheduleDetail> findScheduleDetail(@Path("scheduleId") Long scheduleId);
    @GET("/api/v1/user/booking/rating/{ratingId}/audience-type")
    Call<List<AudienceDetail>> findAllAudienceTypeByRating(@Path("ratingId") String ratingId);
    @GET("/api/v1/user/booking/seat-types")
    Call<List<SeatTypeResponse>> findAllSeatTypes();
    @GET("/api/v1/user/booking/schedule/{scheduleId}/seat")
    Call<List<Seat>> findAllSeatBySchedule(@Path("scheduleId") Long scheduleId);
    @GET("/api/v1/user/booking/schedule/{scheduleId}/concession")
    Call<List<ConcessionResponse>> findAllConcessionBySchedule(@Path("scheduleId") Long scheduleId);


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
    @GET("/api/v1/user/booking/allTickets")
    Call<List<TicketResponse>> getAllTickets();
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
