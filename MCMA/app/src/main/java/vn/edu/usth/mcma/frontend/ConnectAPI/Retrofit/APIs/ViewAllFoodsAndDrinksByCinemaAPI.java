package vn.edu.usth.mcma.frontend.ConnectAPI.Retrofit.APIs;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import vn.edu.usth.mcma.frontend.ConnectAPI.Model.Response.ListFoodAndDrinkToOrderingResponse;

public interface ViewAllFoodsAndDrinksByCinemaAPI {
    @GET("/api/v1/user/booking/allFoodsAndDrinksByCinema/{cinemaId}")
    Call<List<ListFoodAndDrinkToOrderingResponse>> ViewFoodsAndDrinks(@Path("cinemaId") int cinemaId);
}
