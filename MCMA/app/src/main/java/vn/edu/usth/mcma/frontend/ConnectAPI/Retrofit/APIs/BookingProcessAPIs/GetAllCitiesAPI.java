package vn.edu.usth.mcma.frontend.ConnectAPI.Retrofit.APIs.BookingProcessAPIs;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import vn.edu.usth.mcma.frontend.ConnectAPI.Model.Response.BookingProcess.CityResponse;
import vn.edu.usth.mcma.frontend.ConnectAPI.Model.Response.ViewCityResponse;

public interface GetAllCitiesAPI {
    @GET("/api/v1/user/view/cityList")
    Call<ViewCityResponse> getAllCities();

    @GET("/api/v1/user/booking/allCitiesByMovie/{movieId}")
    Call<List<CityResponse>> getCitiesByMovieId(@Path("movieId") Long movieId);
}
