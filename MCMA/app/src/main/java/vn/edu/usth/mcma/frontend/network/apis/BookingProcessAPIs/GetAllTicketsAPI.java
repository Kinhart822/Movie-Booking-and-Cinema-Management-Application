package vn.edu.usth.mcma.frontend.network.apis.BookingProcessAPIs;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import vn.edu.usth.mcma.frontend.dto.Response.BookingProcess.TicketResponse;


public interface GetAllTicketsAPI {
    @GET("/api/v1/user/booking/allTickets")
    Call<List<TicketResponse>> getAllTickets();
}
