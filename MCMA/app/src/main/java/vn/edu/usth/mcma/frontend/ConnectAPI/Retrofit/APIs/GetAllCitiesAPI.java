package vn.edu.usth.mcma.frontend.ConnectAPI.Retrofit.APIs;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import vn.edu.usth.mcma.frontend.ConnectAPI.Model.Response.ViewCityResponse;

public interface GetAllCitiesAPI {
    @GET("/api/v1/user/view/cityList")
    Call<ViewCityResponse> getAllCities();
}
