package vn.edu.usth.mcma.frontend.Personal;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.usth.mcma.R;
import vn.edu.usth.mcma.frontend.ConnectAPI.Retrofit.APIs.AuthenticationApi;
import vn.edu.usth.mcma.frontend.ConnectAPI.Retrofit.RetrofitService;
import vn.edu.usth.mcma.frontend.MainActivity;

public class PersonalFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_personal, container, false);

        ImageButton closeButton = v.findViewById(R.id.close_button);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getActivity() instanceof vn.edu.usth.mcma.frontend.MainActivity){
                    ((vn.edu.usth.mcma.frontend.MainActivity) getActivity()).close_to_home_page();
                }
            }
        });

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
                Intent i = new Intent(requireContext(),vn.edu.usth.mcma.frontend.Personal.Account_Information_Activity.class );
                startActivity(i);
            }
        });

        LinearLayout to_changepass_account = v.findViewById(R.id.account_information_change_password);
        to_changepass_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(requireContext(),vn.edu.usth.mcma.frontend.Personal.Change_Password_Account_Info_Activity.class );
                startActivity(i);
            }
        });

        LinearLayout to_booking_history = v.findViewById(R.id.account_information_booking_history);
        to_booking_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(requireContext(),vn.edu.usth.mcma.frontend.Personal.Booking_History_Activity.class );
                startActivity(i);
            }
        });

        LinearLayout logout_button = v.findViewById(R.id.account_information_log_out);
        logout_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                RetrofitService retrofitService = new RetrofitService(requireActivity());
                AuthenticationApi authenticationApi = retrofitService.getRetrofit().create(AuthenticationApi.class);
                authenticationApi.logout().enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()) {
                            // Logout successful, navigate to LoginFragment
                            Toast.makeText(getContext(), "Logged out successfully", Toast.LENGTH_SHORT).show();

                            Fragment loginFragment = new vn.edu.usth.mcma.frontend.Login.LoginFragment();
                            FragmentTransaction fragmentTransaction = requireActivity().getSupportFragmentManager().beginTransaction();
                            fragmentTransaction.replace(android.R.id.content, loginFragment);
                            fragmentTransaction.commit();
                        } else {
                            // Handle failure response
                            Toast.makeText(getContext(), "Failed to log out. Please try again.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(getContext(), "Network error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

//                Fragment loginFragment = new vn.edu.usth.mcma.frontend.Login.LoginFragment();
//                FragmentTransaction fragmentTransaction = requireActivity().getSupportFragmentManager().beginTransaction();
//                fragmentTransaction.replace(android.R.id.content, loginFragment);
//                fragmentTransaction.commit();

            }
        });

        return v;
    }

}
