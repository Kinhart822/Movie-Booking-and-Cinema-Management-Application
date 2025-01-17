package vn.edu.usth.mcma.frontend.component.Personal;

import static android.content.Context.MODE_PRIVATE;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.usth.mcma.R;
import vn.edu.usth.mcma.frontend.constant.SharedPreferencesKey;
import vn.edu.usth.mcma.frontend.network.ApiService;
import vn.edu.usth.mcma.frontend.component.Login.LoginFragment;
import vn.edu.usth.mcma.frontend.network.AuthPrefsManager;

public class PersonalFragment extends Fragment {
    AuthPrefsManager authPrefsManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_personal, container, false);
        authPrefsManager = new AuthPrefsManager(requireContext());

        LinearLayout to_edit_update = v.findViewById(R.id.account_information_edit_update);
        to_edit_update.setOnClickListener(view -> {
            Intent i = new Intent(requireContext(), Edit_Update_Account_Info_Activity.class);
            startActivity(i);
        });

        LinearLayout to_detail = v.findViewById(R.id.account_information_details);
        to_detail.setOnClickListener(view -> {
            Intent i = new Intent(requireContext(), Account_Information_Activity.class);
            startActivity(i);
        });

        LinearLayout changePasswordLayout = v.findViewById(R.id.account_information_change_password);
        changePasswordLayout.setOnClickListener(view -> {
            Intent i = new Intent(requireContext(), Change_Password_Account_Info_Activity.class);
            startActivity(i);
        });

        LinearLayout to_booking_history = v.findViewById(R.id.account_information_booking_history);
        to_booking_history.setOnClickListener(view -> {
            Intent i = new Intent(requireContext(), Booking_History_Activity.class);
            startActivity(i);
        });

        LinearLayout to_feedback = v.findViewById(R.id.information_view_feedback);
        to_feedback.setOnClickListener(view -> {
            // Inflate the dialog layout
            LayoutInflater inflater1 = LayoutInflater.from(requireContext());
            View dialogView = inflater1.inflate(R.layout.dialog_feedback_fragment, null);

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
            btnViewMovieFeedback.setOnClickListener(v10 -> {
                // Navigate to Movie_Feedback_Activity
                Intent intent = new Intent(requireContext(), Movie_Feedback_Activity.class);
                requireContext().startActivity(intent);
                dialog.dismiss(); // Close the dialog
            });

            btnViewYourFeedback.setOnClickListener(v10 -> {
                // Navigate to ViewFeedback_Activity
                Intent intent = new Intent(requireContext(), ViewFeedback_Activity.class);
                requireContext().startActivity(intent);
                dialog.dismiss(); // Close the dialog
            });

            btnCancelFeedback.setOnClickListener(v10 -> {
                // Dismiss the dialog
                dialog.dismiss();
            });

            // Show the dialog
            dialog.show();
        });




        LinearLayout signOutButton = v.findViewById(R.id.account_information_sign_out);
        signOutButton.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
            View dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_sign_out, null);

            Button confirmButton = dialogView.findViewById(R.id.btn_confirm_sign_out);
            Button cancelButton = dialogView.findViewById(R.id.btn_cancel_sign_out);

            builder.setView(dialogView);
            AlertDialog dialog = builder.create();

            confirmButton.setOnClickListener(v8 -> {
                signOut();
                dialog.dismiss();
            });

            cancelButton.setOnClickListener(v9 -> dialog.dismiss());
            dialog.show();
        });

        LinearLayout delete_account_button = v.findViewById(R.id.account_information_delete_account);
        delete_account_button.setOnClickListener(v7 -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
            View dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_delete_account, null);

            Button confirmButton = dialogView.findViewById(R.id.btn_confirm_delete_account);
            Button cancelButton = dialogView.findViewById(R.id.btn_cancel_delete_account);

            builder.setView(dialogView);
            AlertDialog dialog = builder.create();

            confirmButton.setOnClickListener(v6 -> {
//                        Fragment loginFragment = new vn.edu.usth.mcma.frontend.Login.LoginFragment();
//                        FragmentTransaction fragmentTransaction = requireActivity().getSupportFragmentManager().beginTransaction();
//                        fragmentTransaction.replace(android.R.id.content, loginFragment);
//                        fragmentTransaction.commit();

                deleteAccount();

                dialog.dismiss();
            });

            cancelButton.setOnClickListener(v5 -> dialog.dismiss());

            dialog.show();
        });

        LinearLayout management_booking_button = v.findViewById(R.id.management_booking_button);
        management_booking_button.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
            View dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_management_booking, null);

            Button cancelBookingButton = dialogView.findViewById(R.id.btn_cancel_booking);
            Button revokeCancelBookingButton = dialogView.findViewById(R.id.btn_revoke_cancel_booking);
            Button deleteBookingButton = dialogView.findViewById(R.id.btn_delete_booking);
            Button exitButton = dialogView.findViewById(R.id.btn_exit);

            builder.setView(dialogView);
            AlertDialog dialog = builder.create();

            cancelBookingButton.setOnClickListener(v4 -> {
                Intent intent = new Intent(requireContext(), Cancel_Booking_Activity.class);
                requireContext().startActivity(intent);
                dialog.dismiss();
            });

            revokeCancelBookingButton.setOnClickListener(v3 -> {
                Intent intent = new Intent(requireContext(), Revoke_Cancel_Booking_Activity.class);
                requireContext().startActivity(intent);
                dialog.dismiss();
            });

            deleteBookingButton.setOnClickListener(v2 -> {
                Intent intent = new Intent(requireContext(), Delete_Booking_Activity.class);
                requireContext().startActivity(intent);
                dialog.dismiss();
            });

            exitButton.setOnClickListener(v1 -> dialog.dismiss());

            dialog.show();
        });

        return v;
    }

    private void signOut() {
        ApiService
                .getAuthApi(requireContext())
                .signOut()
                .enqueue(new Callback<>() {
                    @Override
                    public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                        if (response.isSuccessful()) {
                            // signOut successfully
                            authPrefsManager.removeAll();
                            Toast.makeText(requireContext(), "Sign out successfully", Toast.LENGTH_SHORT).show();

                            // Navigate to login fragment
                            Fragment loginFragment = new LoginFragment();
                            FragmentTransaction fragmentTransaction = requireActivity().getSupportFragmentManager().beginTransaction();
                            fragmentTransaction.replace(android.R.id.content, loginFragment);
                            fragmentTransaction.commit();
                        } else {
                            // Log server error and notify the user
                            Log.e("LogAccount", "Failed to sign out. Response code: " + response.code());
                            Toast.makeText(requireContext(), "Failed to sign out: " + response.message(), Toast.LENGTH_LONG).show();
                        }
                    }
                    @Override
                    public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                        Log.e("Sign out", "API call failed", t);
                        Toast.makeText(requireContext(), "Failed to sign out: " + t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void deleteAccount() {
        int userId = getUserIdFromPreferences();
        Log.d("DeleteAccount", "Deleting account with userId: " + userId);
        ApiService
                .getAccountApi(requireContext())
                .deleteAccount(userId).enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                if (response.isSuccessful()) {
                    // Account deleted successfully
                    Toast.makeText(requireContext(), "Account deleted successfully", Toast.LENGTH_SHORT).show();

                    // Navigate to login fragment
                    Fragment loginFragment = new LoginFragment();
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
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                Log.e("DeleteAccount", "API call failed", t);
                Toast.makeText(requireContext(), "Failed to delete account: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

    private int getUserIdFromPreferences() {
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences(SharedPreferencesKey.PROFILE.name(), MODE_PRIVATE);
        return sharedPreferences.getInt(SharedPreferencesKey.PROFILE_ID.name(), 0); // Default userId is 0 if not set
    }
}
