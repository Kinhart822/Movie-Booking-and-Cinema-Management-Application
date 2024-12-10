package vn.edu.usth.mcma.frontend.Login;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import vn.edu.usth.mcma.R;
import vn.edu.usth.mcma.frontend.MainActivity;

public class LoginFragment extends Fragment {

    private EditText editTextEmail, editTextPassword;
    private Button buttonLogin;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        // Chỗ này tránh click lỗi sẽ nhảy sang layout khác
        view.setOnClickListener(v -> {

        });

        editTextEmail = view.findViewById(R.id.editText);
        editTextPassword = view.findViewById(R.id.editText2);
        buttonLogin = view.findViewById(R.id.login_button);
        ImageView imageView = view.findViewById(R.id.imageView);

        buttonLogin.setOnClickListener(v -> {
            String email = editTextEmail.getText().toString();
            String password = editTextPassword.getText().toString();

            if (validateLogin(email, password)) {
                SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("Aloo", getContext().MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("isLoggedIn", true);
                editor.apply();

                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                requireActivity().finish();
            } else {
                Toast.makeText(getActivity(), "Enter email or phone number to log in", Toast.LENGTH_SHORT).show();
            }
        });

        TextView create_account = view.findViewById(R.id.create_account);
        create_account.setOnClickListener(view1 -> {
            Intent intent = new Intent(requireContext(), vn.edu.usth.mcma.frontend.Login.Register_Activity.class);
            startActivity(intent);
        });

        TextView forgot_password = view.findViewById(R.id.forgot_password);
        forgot_password.setOnClickListener(view12 -> {
            Intent intent = new Intent(requireContext(), vn.edu.usth.mcma.frontend.Login.ForgotPassword_Activity.class);
            startActivity(intent);
        });

        imageView.setOnClickListener(v -> {

        });

        return view;
    }

    private boolean validateLogin(String email, String password) {
        return !email.isEmpty() && !password.isEmpty();
    }
}
