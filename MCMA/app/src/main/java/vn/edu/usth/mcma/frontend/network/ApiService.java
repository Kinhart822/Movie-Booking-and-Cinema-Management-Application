package vn.edu.usth.mcma.frontend.network;

import android.content.Context;

import vn.edu.usth.mcma.frontend.network.apis.AccountApi;
import vn.edu.usth.mcma.frontend.network.apis.AuthApi;
import vn.edu.usth.mcma.frontend.network.apis.BookingApi;
import vn.edu.usth.mcma.frontend.network.apis.MovieApi;
import vn.edu.usth.mcma.frontend.network.apis.CinemaApi;

public class ApiService {
    private static AuthApi authApi;
    private static AccountApi accountApi;
    private static MovieApi movieApi;
    private static CinemaApi cinemaApi;
    private static vn.edu.usth.mcma.frontend.network.apis.BookingApi bookingApi;

    public static AuthApi getAuthApi(Context context) {
        if (authApi == null) {
            authApi = ApiClient.getClient(context).create(AuthApi.class);
        }
        return authApi;
    }
    public static AccountApi getAccountApi(Context context) {
        if (accountApi == null) {
            accountApi = ApiClient.getClient(context).create(AccountApi.class);
        }
        return accountApi;
    }
    public static MovieApi getMovieApi(Context context) {
        if (movieApi == null) {
            movieApi = ApiClient.getClient(context).create(MovieApi.class);
        }
        return movieApi;
    }
    public static CinemaApi getCinemaApi(Context context) {
        if (cinemaApi == null) {
            cinemaApi = ApiClient.getClient(context).create(CinemaApi.class);
        }
        return cinemaApi;
    }
    public static BookingApi getBookingApi(Context context) {
        if (bookingApi == null) {
            bookingApi = ApiClient.getClient(context).create(BookingApi.class);
        }
        return bookingApi;
    }
}
