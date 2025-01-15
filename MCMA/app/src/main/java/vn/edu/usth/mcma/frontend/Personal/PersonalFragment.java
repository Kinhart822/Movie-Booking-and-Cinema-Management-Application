package vn.edu.usth.mcma.frontend.Personal;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import vn.edu.usth.mcma.R;

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

        LinearLayout to_coupon_page = v.findViewById(R.id.coupon_page);
        to_coupon_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(requireContext(), vn.edu.usth.mcma.frontend.Coupon.Coupon_Activity.class);
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
                        Fragment loginFragment = new vn.edu.usth.mcma.frontend.Login.LoginFragment();
                        FragmentTransaction fragmentTransaction = requireActivity().getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(android.R.id.content, loginFragment);
                        fragmentTransaction.commit();

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
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                View dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_delete_account, null);

                Button confirmButton = dialogView.findViewById(R.id.btn_confirm_delete_account);
                Button cancelButton = dialogView.findViewById(R.id.btn_cancel_delete_account);

                builder.setView(dialogView);
                AlertDialog dialog = builder.create();

                confirmButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Fragment loginFragment = new vn.edu.usth.mcma.frontend.Login.LoginFragment();
                        FragmentTransaction fragmentTransaction = requireActivity().getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(android.R.id.content, loginFragment);
                        fragmentTransaction.commit();

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
}
