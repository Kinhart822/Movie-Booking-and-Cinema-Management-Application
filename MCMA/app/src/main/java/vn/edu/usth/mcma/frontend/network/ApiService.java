package vn.edu.usth.mcma.frontend.network;

import android.content.Context;

import vn.edu.usth.mcma.frontend.network.apis.AccountApi;
import vn.edu.usth.mcma.frontend.network.apis.AuthApi;
import vn.edu.usth.mcma.frontend.network.apis.BookingApi;
import vn.edu.usth.mcma.frontend.network.apis.MovieApi;
import vn.edu.usth.mcma.frontend.network.apis.CinemaApi;

public class ApiService {
    private static AuthApi unauthAuthApi;
    private static AuthApi authApi;
    private static AccountApi accountApi;
    private static MovieApi movieApi;
    private static CinemaApi cinemaApi;
    private static BookingApi bookingApi;

    public static AuthApi getUnauthAuthApi() {
        if (unauthAuthApi == null) {
            unauthAuthApi = ApiClient.getUnauthenticatedClient().create(AuthApi.class);
        }
        return unauthAuthApi;
    }
    public static AuthApi getAuthApi(Context context) {
        if (authApi == null) {
            authApi = ApiClient.getAuthenticatedClient(context).create(AuthApi.class);
        }
        return authApi;
    }
    public static AccountApi getAccountApi(Context context) {
        if (accountApi == null) {
            accountApi = ApiClient.getAuthenticatedClient(context).create(AccountApi.class);
        }
        return accountApi;
    }
    public static MovieApi getMovieApi(Context context) {
        if (movieApi == null) {
            movieApi = ApiClient.getAuthenticatedClient(context).create(MovieApi.class);
        }
        return movieApi;
    }
    public static CinemaApi getCinemaApi(Context context) {
        if (cinemaApi == null) {
            cinemaApi = ApiClient.getAuthenticatedClient(context).create(CinemaApi.class);
        }
        return cinemaApi;
    }
    public static BookingApi getBookingApi(Context context) {
        if (bookingApi == null) {
            bookingApi = ApiClient.getAuthenticatedClient(context).create(BookingApi.class);
        }
        return bookingApi;
    }
}
