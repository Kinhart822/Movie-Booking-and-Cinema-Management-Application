package vn.edu.usth.mcma.frontend.component.account;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.time.Instant;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.usth.mcma.R;
import vn.edu.usth.mcma.frontend.constant.IntentKey;
import vn.edu.usth.mcma.frontend.dto.account.SendOtpRequest;
import vn.edu.usth.mcma.frontend.dto.account.OtpDueDate;
import vn.edu.usth.mcma.frontend.network.ApiService;

public class ForgotPasswordStepOneActivity extends AppCompatActivity {
    private static final String TAG = ForgotPasswordStepOneActivity.class.getName();
    private EditText emailEditText;
    private String sessionId;
    private Instant otpDueDate;
    private Button nextButton;
    private boolean waitForOtp;
    private Handler nextHandler;
    private int dotCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password_step_one);
        ImageButton backButton = findViewById(R.id.button_back);
        emailEditText = findViewById(R.id.edit_text_email);
        waitForOtp = false;
        nextButton = findViewById(R.id.button_next);
        nextHandler = new Handler(Looper.getMainLooper());
        dotCount = 0;

        backButton
                .setOnClickListener(v -> onBackPressed());
        prepareNextButton();
    }
    @SuppressLint("SetTextI18n")
    private void prepareNextButton() {
        nextButton.setText("Next");
        nextButton
                .setOnClickListener(v -> {
                    stopNextButton();
                    forgotPasswordBegin();
                });
    }
    private void stopNextButton() {
        nextButton.setOnClickListener(null);
        String s = "Please wait";
        waitForOtp = true;
        nextHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!waitForOtp) return;
                dotCount = (dotCount + 1) % 4;
                String text = s;
                text = text + new String(new char[dotCount]).replace("\0", ".");
                nextButton.setText(text);
                nextHandler.postDelayed(this, 500);
            }
        }, 500);
    }
    /*
     * this calls postForgotPasswordBegin
     */
    private void forgotPasswordBegin() {
        sessionId = UUID.randomUUID().toString() + "-" + SystemClock.elapsedRealtime();
        ApiService
                .getAccountApi(this)
                .forgotPasswordBegin(SendOtpRequest
                        .builder()
                        .email(emailEditText.getText().toString())
                        .sessionId(sessionId)
                        .build())
                .enqueue(new Callback<>() {
                    @Override
                    public void onResponse(@NonNull Call<OtpDueDate> call, @NonNull Response<OtpDueDate> response) {
                        if (!response.isSuccessful() || response.body() == null) {
                            Log.e(TAG, "signUpCheckOtp onResponse: code not 200 || body is null");
                            return;
                        }
                        waitForOtp = false;
                        otpDueDate = Instant.parse(response.body().getOtpDueDate());
                        postForgotPasswordBegin();
                    }
                    @Override
                    public void onFailure(@NonNull Call<OtpDueDate> call, @NonNull Throwable throwable) {
                        Log.d(TAG, "forgotPasswordBegin onFailure: " + throwable);
                    }
                });

    }
    private void postForgotPasswordBegin() {
        Intent intent = new Intent(ForgotPasswordStepOneActivity.this, ForgotPasswordStepTwoActivity.class);
        intent.putExtra(IntentKey.FORGOT_PASSWORD_EMAIL.name(), emailEditText.getText().toString());
        intent.putExtra(IntentKey.FORGOT_PASSWORD_OTP_DUE_DATE.name(), otpDueDate);
        intent.putExtra(IntentKey.FORGOT_PASSWORD_SESSION_ID.name(), sessionId);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        waitForOtp = false;
        nextHandler.removeCallbacksAndMessages(null);
    }

    @Override
    protected void onResume() {
        super.onResume();
        prepareNextButton();
    }
}