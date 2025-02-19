package vn.edu.usth.mcma.frontend.network.apis;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import vn.edu.usth.mcma.frontend.dto.cinema.CinemaDetailShort;
import vn.edu.usth.mcma.frontend.dto.response.BookingProcess.CinemaResponse;
import vn.edu.usth.mcma.frontend.dto.response.BookingProcess.ScreenResponse;
import vn.edu.usth.mcma.frontend.dto.response.ListFoodAndDrinkToOrderingResponse;
import vn.edu.usth.mcma.frontend.dto.response.ScheduleSelectedByCinemaResponse;
import vn.edu.usth.mcma.frontend.dto.response.ViewCinemaResponse;

public interface CinemaApi {
    @GET("/api/v1/user/view/city/{cityId}/cinema")
    Call<List<CinemaDetailShort>> findAllCinemaByCity(@Path("cityId") Long cityId);

    @GET("/api/v1/user/view/cinemaList")
    Call<ViewCinemaResponse> getCinemaList();

    @GET("/api/v1/user/view/cinemaListByCity/{cityId}")
    Call<ViewCinemaResponse> getCinemaListByCity(@Path("cityId") int cityId);
    @GET("/api/v1/user/view/allScheduleByCinema/{cinemaId}")
    Call<ScheduleSelectedByCinemaResponse> getScheduleByCinema(@Path("cinemaId") Long cinemaId);
    @GET("/api/v1/user/booking/allFoodsAndDrinksByCinema/{cinemaId}")
    Call<List<ListFoodAndDrinkToOrderingResponse>> viewFoodsAndDrinks(@Path("cinemaId") int cinemaId);

    @GET("/api/v1/user/booking/allCinemasByMovieAndCity")
    Call<List<CinemaResponse>> getCinemasByMovieIdAndCityId(@Query("movieId") Long movieId, @Query("cityId") Long cityId);
    @GET("/api/v1/user/booking/allScreenByCinema/{cinemaId}")
    Call<List<ScreenResponse>> getScreenByCinemaId(@Path("cinemaId") Long cinemaId);
}
