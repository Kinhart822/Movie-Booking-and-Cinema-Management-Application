package vn.edu.usth.mcma.frontend.ConnectAPI.Retrofit.APIs.BookingProcessAPIs;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import vn.edu.usth.mcma.frontend.ConnectAPI.Model.Response.BookingProcess.TicketResponse;


public interface GetAllTicketsAPI {
    @GET("/api/v1/user/booking/allTickets")
    Call<List<TicketResponse>> getAllTickets();
}
