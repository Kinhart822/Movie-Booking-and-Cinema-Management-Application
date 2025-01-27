package vn.edu.usth.mcma.frontend.network.apis;

import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import vn.edu.usth.mcma.frontend.dto.home.Advertisement;
import vn.edu.usth.mcma.frontend.dto.movie.GenreShort;
import vn.edu.usth.mcma.frontend.dto.movie.MovieDetail;
import vn.edu.usth.mcma.frontend.dto.request.MovieRespondRequest;
import vn.edu.usth.mcma.frontend.dto.response.BookingProcess.CityResponse;
import vn.edu.usth.mcma.frontend.dto.response.BookingProcess.CouponResponse;
import vn.edu.usth.mcma.frontend.dto.response.ComingSoonResponse;
import vn.edu.usth.mcma.frontend.dto.movie.MovieDetailShort;
import vn.edu.usth.mcma.frontend.dto.response.MovieRespondResponse;
import vn.edu.usth.mcma.frontend.dto.response.MovieResponse;
import vn.edu.usth.mcma.frontend.dto.response.NowShowingResponse;
import vn.edu.usth.mcma.frontend.dto.response.Schedule;
import vn.edu.usth.mcma.frontend.dto.response.SearchMovieByNameResponse;

public interface MovieApi {
    @GET("/api/v1/user/view/movie/advertisement")
    Call<List<Advertisement>> findAllAdvertisement();
    @GET("/api/v1/user/view/movie/now-showing")
    Call<List<MovieDetailShort>> findAllNowShowing();
    @GET("/api/v1/user/view/movie/coming-soon")
    Call<List<MovieDetailShort>> findAllComingSoon();
    @GET("/api/v1/user/view/movie/{id}")
    Call<MovieDetail> findMovieDetail(@Path("id") Long id);

    @GET("/api/v1/user/view/genre")
    Call<List<GenreShort>> findAllGenre();
    @POST("/api/v1/user/view/movie/genre")// GET will not allow @Body
    Call<List<MovieDetailShort>> findAllMovieByGenre(@Query("name") String name, @Body Set<Long> ids);

    @POST("/api/v1/user/movieRespond/add")
    Call<MovieRespondRequest> addRespond(@Body MovieRespondRequest movieRespondRequest);
    @GET("/api/v1/user/view/comingSoonMovies")
    Call<List<ComingSoonResponse>> getAvailableComingSoonMovies();
    @GET("/api/v1/user/view/viewMovieRespondByMovie/{movieId}")
    Call<List<MovieRespondResponse>> viewMovieRespondByMovie(@Path("movieId") int movieId);
    @GET("/api/v1/user/information/movie-information/{movieId}")
    Call<MovieResponse> getAllInformationOfSelectedMovie(@Path("movieId") long movieId);

    @GET("/api/v1/user/view/getAllMovieInformationBySelectedDateSchedule")
    Call<List<MovieResponse>> getAllMovieBySelectedDate(@Query("date") String date);
    @GET("/api/v1/user/view/nowShowingMovies")
    Call<List<NowShowingResponse>> getAvailableNowShowingMovies();

    @GET("/api/v1/user/booking/allCitiesByMovie/{movieId}")
    Call<List<CityResponse>> getCitiesByMovieId(@Path("movieId") Long movieId);
    @GET("/api/v1/user/booking/allCouponsByMovie/{movieId}")
    Call<List<CouponResponse>> getAllCouponsByMovie(@Path("movieId") int movieId);
    @GET("/api/v1/user/booking/allSchedulesByMovieAndCinemaAndScreen")
    Call<List<Schedule>> getAllSchedulesByMovieAndScreen(@Query("movieId") Long movieId, @Query("screenId") Long screenId);
}
