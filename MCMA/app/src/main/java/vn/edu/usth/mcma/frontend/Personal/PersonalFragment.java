package vn.edu.usth.mcma.frontend.Personal;

import static android.content.Context.MODE_PRIVATE;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.usth.mcma.R;
import vn.edu.usth.mcma.frontend.ConnectAPI.Retrofit.APIs.AuthenticationApi;
import vn.edu.usth.mcma.frontend.ConnectAPI.Retrofit.APIs.DeleteAccountAPI;
import vn.edu.usth.mcma.frontend.ConnectAPI.Retrofit.RetrofitService;

public class PersonalFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_personal, container, false);

        LinearLayout to_edit_update = v.findViewById(R.id.account_information_edit_update);
        to_edit_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(requireContext(), vn.edu.usth.mcma.frontend.Personal.Edit_Update_Account_Info_Activity.class);
                startActivity(i);
            }
        });

        LinearLayout to_detail = v.findViewById(R.id.account_information_details);
        to_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(requireContext(), vn.edu.usth.mcma.frontend.Personal.Account_Information_Activity.class);
                startActivity(i);
            }
        });

        LinearLayout to_changepass_account = v.findViewById(R.id.account_information_change_password);
        to_changepass_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(requireContext(), vn.edu.usth.mcma.frontend.Personal.Change_Password_Account_Info_Activity.class);
                startActivity(i);
            }
        });

        LinearLayout to_booking_history = v.findViewById(R.id.account_information_booking_history);
        to_booking_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(requireContext(), vn.edu.usth.mcma.frontend.Personal.Booking_History_Activity.class);
                startActivity(i);
            }
        });

        LinearLayout to_feedback = v.findViewById(R.id.information_view_feedback);
        to_feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Inflate the dialog layout
                LayoutInflater inflater = LayoutInflater.from(requireContext());
                View dialogView = inflater.inflate(R.layout.dialog_feedback_fragment, null);

                // Create the dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                builder.setView(dialogView);

                // Initialize the dialog
                AlertDialog dialog = builder.create();

                // Initialize buttons in the dialog
                Button btnViewMovieFeedback = dialogView.findViewById(R.id.btn_viewmovie_feedback);
                Button btnViewYourFeedback = dialogView.findViewById(R.id.btn_viewyour_feedback);
                Button btnCancelFeedback = dialogView.findViewById(R.id.btn_cancel_feedback);

                // Set click listeners for the buttons
                btnViewMovieFeedback.setOnClickListener(v -> {
                    // Navigate to Movie_Feedback_Activity
                    Intent intent = new Intent(requireContext(), Movie_Feedback_Activity.class);
                    requireContext().startActivity(intent);
                    dialog.dismiss(); // Close the dialog
                });

                btnViewYourFeedback.setOnClickListener(v -> {
                    // Navigate to ViewFeedback_Activity
                    Intent intent = new Intent(requireContext(), ViewFeedback_Activity.class);
                    requireContext().startActivity(intent);
                    dialog.dismiss(); // Close the dialog
                });

                btnCancelFeedback.setOnClickListener(v -> {
                    // Dismiss the dialog
                    dialog.dismiss();
                });

                // Show the dialog
                dialog.show();
            }
        });




        LinearLayout logout_button = v.findViewById(R.id.account_information_log_out);
        logout_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                View dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_logout, null);

                Button confirmButton = dialogView.findViewById(R.id.btn_confirm_logout);
                Button cancelButton = dialogView.findViewById(R.id.btn_cancel_logout);

                builder.setView(dialogView);
                AlertDialog dialog = builder.create();

                confirmButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        Fragment loginFragment = new vn.edu.usth.mcma.frontend.Login.LoginFragment();
//                        FragmentTransaction fragmentTransaction = requireActivity().getSupportFragmentManager().beginTransaction();
//                        fragmentTransaction.replace(android.R.id.content, loginFragment);
//                        fragmentTransaction.commit();
                        Logout();

                        dialog.dismiss();
                    }
                });

                cancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });


                dialog.show();
            }
        });

        LinearLayout delete_account_button = v.findViewById(R.id.account_information_delete_account);
        delete_account_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                View dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_delete_account, null);

                Button confirmButton = dialogView.findViewById(R.id.btn_confirm_delete_account);
                Button cancelButton = dialogView.findViewById(R.id.btn_cancel_delete_account);

                builder.setView(dialogView);
                AlertDialog dialog = builder.create();

                confirmButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        Fragment loginFragment = new vn.edu.usth.mcma.frontend.Login.LoginFragment();
//                        FragmentTransaction fragmentTransaction = requireActivity().getSupportFragmentManager().beginTransaction();
//                        fragmentTransaction.replace(android.R.id.content, loginFragment);
//                        fragmentTransaction.commit();

                        deleteAccount();

                        dialog.dismiss();
                    }
                });

                cancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });

        LinearLayout management_booking_button = v.findViewById(R.id.management_booking_button);
        management_booking_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                View dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_management_booking, null);

                Button cancelBookingButton = dialogView.findViewById(R.id.btn_cancel_booking);
                Button revokeCancelBookingButton = dialogView.findViewById(R.id.btn_revoke_cancel_booking);
                Button deleteBookingButton = dialogView.findViewById(R.id.btn_delete_booking);
                Button exitButton = dialogView.findViewById(R.id.btn_exit);

                builder.setView(dialogView);
                AlertDialog dialog = builder.create();

                cancelBookingButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(requireContext(), vn.edu.usth.mcma.frontend.Personal.Cancel_Booking_Activity.class);
                        requireContext().startActivity(intent);
                        dialog.dismiss();
                    }
                });

                revokeCancelBookingButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(requireContext(), vn.edu.usth.mcma.frontend.Personal.Revoke_Cancel_Booking_Activity.class);
                        requireContext().startActivity(intent);
                        dialog.dismiss();
                    }
                });

                deleteBookingButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(requireContext(), vn.edu.usth.mcma.frontend.Personal.Delete_Booking_Activity.class);
                        requireContext().startActivity(intent);
                        dialog.dismiss();
                    }
                });

                exitButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });

        return v;
    }

    private void Logout() {
        RetrofitService retrofitService = new RetrofitService(requireContext());
        AuthenticationApi authenticationApi = retrofitService.getRetrofit().create(AuthenticationApi.class);
        authenticationApi.logout().enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    // Logout successfully
                    Toast.makeText(requireContext(), "Log out successfully", Toast.LENGTH_SHORT).show();

                    // Navigate to login fragment
                    Fragment loginFragment = new vn.edu.usth.mcma.frontend.Login.LoginFragment();
                    FragmentTransaction fragmentTransaction = requireActivity().getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(android.R.id.content, loginFragment);
                    fragmentTransaction.commit();
                } else {
                    // Log server error and notify the user
                    Log.e("LogAccount", "Failed to log out. Response code: " + response.code());
                    Toast.makeText(requireContext(), "Failed to log out: " + response.message(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("Log out", "API call failed", t);
                Toast.makeText(requireContext(), "Failed to log out: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void deleteAccount() {
        int userId = getUserIdFromPreferences();
        Log.d("DeleteAccount", "Deleting account with userId: " + userId);
        RetrofitService retrofitService = new RetrofitService(requireContext());
        DeleteAccountAPI deleteAccountAPI = retrofitService.getRetrofit().create(DeleteAccountAPI.class);
        deleteAccountAPI.deleteAccount(userId).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    // Account deleted successfully
                    Toast.makeText(requireContext(), "Account deleted successfully", Toast.LENGTH_SHORT).show();

                    // Navigate to login fragment
                    Fragment loginFragment = new vn.edu.usth.mcma.frontend.Login.LoginFragment();
                    FragmentTransaction fragmentTransaction = requireActivity().getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(android.R.id.content, loginFragment);
                    fragmentTransaction.commit();
                } else {
                    // Log server error and notify the user
                    Log.e("DeleteAccount", "Failed to delete account. Response code: " + response.code());
                    Toast.makeText(requireContext(), "Failed to delete account: " + response.message(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("DeleteAccount", "API call failed", t);
                Toast.makeText(requireContext(), "Failed to delete account: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

    private int getUserIdFromPreferences() {
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("ProfilePrefs", MODE_PRIVATE);
        return sharedPreferences.getInt("userId", 0); // Default userId is 0 if not set
    }
}
