package vn.edu.usth.mcma.frontend.network.apis;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import vn.edu.usth.mcma.frontend.dto.Response.BookingProcess.CinemaResponse;
import vn.edu.usth.mcma.frontend.dto.Response.BookingProcess.ScreenResponse;
import vn.edu.usth.mcma.frontend.dto.Response.ListFoodAndDrinkToOrderingResponse;
import vn.edu.usth.mcma.frontend.dto.Response.ScheduleSelectedByCinemaResponse;
import vn.edu.usth.mcma.frontend.dto.Response.SeatTypeResponse;
import vn.edu.usth.mcma.frontend.dto.Response.Seat;
import vn.edu.usth.mcma.frontend.dto.Response.ViewCinemaResponse;

public interface CinemaApi {
    @GET("/api/v1/user/booking/seat-types")
    Call<List<SeatTypeResponse>> getAllSeatTypes();
    @GET("/api/v1/user/booking/seat/{screenId}")
    Call<List<Seat>> getAllSeatsByScreenId(@Path("screenId") Long screenId);
    @GET("/api/v1/user/view/cinemaList")
    Call<ViewCinemaResponse> getCinemaList();

    @GET("/api/v1/user/view/cinemaListByCity/{cityId}")
    Call<ViewCinemaResponse> getCinemaListByCity(@Path("cityId") int cityId);
    @GET("/api/v1/user/view/allScheduleByCinema/{cinemaId}")
    Call<ScheduleSelectedByCinemaResponse> getScheduleByCinema(@Path("cinemaId") int cinemaId);
    @GET("/api/v1/user/booking/allFoodsAndDrinksByCinema/{cinemaId}")
    Call<List<ListFoodAndDrinkToOrderingResponse>> viewFoodsAndDrinks(@Path("cinemaId") int cinemaId);

    @GET("/api/v1/user/booking/allCinemasByMovieAndCity")
    Call<List<CinemaResponse>> getCinemasByMovieIdAndCityId(@Query("movieId") Long movieId, @Query("cityId") Long cityId);
    @GET("/api/v1/user/booking/allScreenByCinema/{cinemaId}")
    Call<List<ScreenResponse>> getScreenByCinemaId(@Path("cinemaId") Long cinemaId);
}
