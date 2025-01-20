package vn.edu.usth.mcma.frontend.component.account;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

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
import vn.edu.usth.mcma.frontend.dto.account.EmailExistenceResponse;
import vn.edu.usth.mcma.frontend.dto.account.OtpDueDate;
import vn.edu.usth.mcma.frontend.network.ApiService;

public class SignUpStepOneActivity extends AppCompatActivity {
    private static final String TAG = SignUpStepOneActivity.class.getName();
    private boolean isEmailOk;
    private EditText emailEditText;
    private TextView statusIconTextView, statusDescriptionTextView;
    private Handler checkExistHandler, nextHandler;
    private Runnable checkExistRunnable;
    private Instant otpDueDate;
    private String sessionId;
    private Button nextButton;
    private boolean waitForOtp;
    private int dotCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_step_one);
        ImageButton backButton = findViewById(R.id.button_back);
        isEmailOk = false;
        checkExistHandler = new Handler();
        emailEditText = findViewById(R.id.edit_text_email);
        statusIconTextView = findViewById(R.id.text_view_email_status_icon);
        statusDescriptionTextView = findViewById(R.id.text_view_email_status_description);
        nextButton = findViewById(R.id.button_next);
        waitForOtp = false;
        dotCount = 0;
        nextHandler = new Handler();

        backButton
                .setOnClickListener(v -> this.onBackPressed());
        emailEditText
                .addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    }
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        String email = charSequence.toString();
                        if (email.isEmpty()) {
                            isEmailOk = false;
                            statusIconTextView.setText(null);
                            statusDescriptionTextView.setText(null);
                            statusDescriptionTextView.setTextColor(getResources().getColor(R.color.black));
                            return;
                        }
                        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                            isEmailOk = false;
                            statusIconTextView.setText("❌");
                            statusDescriptionTextView.setText("Invalid email format");
                            statusDescriptionTextView.setTextColor(getResources().getColor(R.color.red));
                            return;
                        }
                        statusIconTextView.setText("🤔");
                        statusDescriptionTextView.setText("Checking");
                        statusDescriptionTextView.setTextColor(getResources().getColor(R.color.not_too_bright_yellow));
                        // cancel previous api call if user types again
                        if (checkExistRunnable != null) {
                            checkExistHandler.removeCallbacks(checkExistRunnable);
                        }
                        // schedule new api call
                        checkExistRunnable = () -> checkEmailExistence(email);
                        checkExistHandler.postDelayed(checkExistRunnable, 500);
                    }
                    @Override
                    public void afterTextChanged(Editable editable) {
                    }
                });
        prepareNextButton();
    }
    private void checkEmailExistence(String query) {
        ApiService
                .getAccountApi(this)
                .checkEmailExistence(query)
                .enqueue(new Callback<>() {
                    @Override
                    public void onResponse(@NonNull Call<EmailExistenceResponse> call, @NonNull Response<EmailExistenceResponse> response) {
                        if (!response.isSuccessful() || response.body() == null) {
                            Log.e(TAG, "checkEmailExistence onResponse: code not ok || body is null");
                            return;
                        }
                        boolean result = response.body().isEmailExisted();
                        isEmailOk = !result;
                        statusIconTextView.setText(result
                                ? "❌"
                                : "✔️");
                        statusDescriptionTextView.setText(result
                                ? "This email address is used."
                                : "This email address is unused.");
                        statusDescriptionTextView.setTextColor(getResources().getColor(result
                                ? R.color.red
                                : R.color.green));
                    }
                    @Override
                    public void onFailure(@NonNull Call<EmailExistenceResponse> call, @NonNull Throwable throwable) {
                        Log.e(TAG, "checkEmailExistence onFailure: " + throwable);
                    }
                });
    }
    private void prepareNextButton() {
        nextButton
                .setOnClickListener(v -> {
                    if (!isEmailOk) {
                        Toast.makeText(this, "Your email address needs to be checked", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    stopNextButton();
                    signUpBegin();
                });
    }
    @SuppressLint("SetTextI18n")
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
     * this calls postSignUpBegin
     */
    private void signUpBegin() {
        sessionId = UUID.randomUUID().toString() + "-" + Instant.now().getEpochSecond();
        ApiService
                .getAccountApi(this)
                .signUpBegin(SendOtpRequest
                        .builder()
                        .email(emailEditText.getText().toString())
                        .sessionId(sessionId)
                        .build())
                .enqueue(new Callback<>() {
                    @Override
                    public void onResponse(@NonNull Call<OtpDueDate> call, @NonNull Response<OtpDueDate> response) {
                        if (response.code() == 200) {
                            if (response.body() == null) {
                                Log.e(TAG, "signUpBegin onResponse: response body is null");
                                return;
                            }
                            waitForOtp = false;
                            otpDueDate = Instant.parse(response.body().getOtpDueDate());
                            postSignUpBegin();
                        }
                    }
                    @Override
                    public void onFailure(@NonNull Call<OtpDueDate> call, @NonNull Throwable throwable) {
                        Log.d(TAG, "signUpBegin onFailure: " + throwable);
                    }
                });
    }
    private void postSignUpBegin() {
        Intent intent = new Intent(SignUpStepOneActivity.this, SignUpStepTwoActivity.class);
        intent.putExtra(IntentKey.SIGN_UP_EMAIL.name(), emailEditText.getText().toString());
        intent.putExtra(IntentKey.SIGN_UP_OTP_DUE_DATE.name(), otpDueDate);
        intent.putExtra(IntentKey.SIGN_UP_SESSION_ID.name(), sessionId);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        waitForOtp = false;
        nextHandler.removeCallbacksAndMessages(null);
    }
}