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
                Intent i = new Intent(requireContext(), vn.edu.usth.mcma.frontend.Personal.ViewFeedback_Activity.class);
                startActivity(i);
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

                        // Đóng dialog
                        dialog.dismiss();
                    }
                }); 

                cancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Đóng dialog
                        dialog.dismiss();
                    }
                });

                // Hiển thị dialog
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

                        // Đóng dialog
                        dialog.dismiss();
                    }
                });

                cancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Đóng dialog
                        dialog.dismiss();
                    }
                });

                // Hiển thị dialog
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
                        // Xử lý logic hủy đặt chỗ
                        dialog.dismiss();
                    }
                });

                revokeCancelBookingButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Xử lý logic khôi phục đặt chỗ
                        dialog.dismiss();
                    }
                });

                deleteBookingButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Xử lý logic xóa đặt chỗ
                        dialog.dismiss();
                    }
                });

                exitButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Đóng dialog
                        dialog.dismiss();
                    }
                });

                // Hiển thị dialog
                dialog.show();
            }
        });


        return v;
    }
}
