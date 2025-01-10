package vn.edu.usth.mcma.frontend.network.apis;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Path;


public interface DeleteAccountAPI {
    @DELETE("/api/v1/auth/delete-account/{userId}")
    Call<Void> deleteAccount(@Path("userId") int userId);
}
