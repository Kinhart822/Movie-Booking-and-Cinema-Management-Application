package vn.edu.usth.mcma.frontend.component.account;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.usth.mcma.R;
import vn.edu.usth.mcma.frontend.constant.IntentKey;
import vn.edu.usth.mcma.frontend.dto.request.account.SignUpBegin;
import vn.edu.usth.mcma.frontend.dto.request.account.SignUpCheckOtp;
import vn.edu.usth.mcma.frontend.dto.response.account.VerifyEmailDueDate;
import vn.edu.usth.mcma.frontend.dto.response.account.VerifyEmailOtpCheckResult;
import vn.edu.usth.mcma.frontend.network.ApiService;

public class SignUpStepTwoActivity extends AppCompatActivity {
    private static final String TAG = SignUpStepTwoActivity.class.getName();
    private String email;
    private Instant otpDueDate;
    private String sessionId;
    private TextView infoTextView, resendOtpTextView, timeRemainingTextView;
    private EditText otpEditText;
    private boolean isOtpOk;
    private Button checkButton;
    private boolean waitForResendOtp;
    private Handler checkOtpHandler;
    private int dotCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_step_two);
        email = getIntent().getStringExtra(IntentKey.SIGN_UP_EMAIL.name());
        otpDueDate = (Instant) getIntent().getSerializableExtra(IntentKey.SIGN_UP_OTP_DUE_DATE.name());
        //todo
        System.out.println(otpDueDate);
        sessionId = getIntent().getStringExtra(IntentKey.SIGN_UP_SESSION_ID.name());
        infoTextView = findViewById(R.id.info);
        ImageButton backButton = findViewById(R.id.button_back);
        otpEditText = findViewById(R.id.edit_text_otp);
        isOtpOk = false;
        resendOtpTextView = findViewById(R.id.text_view_resend_otp);
        timeRemainingTextView = findViewById(R.id.text_view_time_remaining);
        checkButton = findViewById(R.id.button_check);
        waitForResendOtp = false;
        dotCount = 0;
        checkOtpHandler = new Handler();

        backButton
                .setOnClickListener(v -> onBackPressed());
        prepareInfo(true);
        prepareTimeRemaining();
        prepareCheckButton();
    }
    private void prepareInfo(boolean isFirstTime) {
        String s = isFirstTime ? "An" : "Another";
        SpannableStringBuilder info = new SpannableStringBuilder();
        info.append(s).append(" OTP was sent to ");
        int start = info.length();
        info.append(email);
        info.append(". Please use it to verify your account.");
        info.setSpan(new StyleSpan(Typeface.BOLD), start, start + email.length(), 0);
        infoTextView.setText(info);
    }
    private void prepareTimeRemaining() {
        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @SuppressLint({"SetTextI18n", "DefaultLocale"})
            @Override
            public void run() {
                Instant now = Instant.now();
                System.out.println(now);
                System.out.println(otpDueDate);
                Log.d(TAG, "prepareTimeRemaining run: "+now.toString()+" "+otpDueDate.toString());
                Duration timeRemaining = Duration.between(now, otpDueDate);
                if (timeRemaining.isNegative() || timeRemaining.isZero()) {
                    checkButton.setOnClickListener(null);
                    timeRemainingTextView.setText("OTP is expired!");
                    resendOtpTextView.setText("Resend OTP");
                    resendOtpTextView.setOnClickListener(v -> resendOtpClicked());
                    return;
                }
                int minutesPart = timeRemaining.toMinutesPart();
                int secondsPart = timeRemaining.toSecondsPart();
                timeRemainingTextView.setText(String.format("Time Remaining: %02d:%02d", minutesPart, secondsPart));
                if (minutesPart == 0 && secondsPart <= 10) {
                    timeRemainingTextView.setTextColor(getResources().getColor(R.color.red));
                }
                handler.postDelayed(this, 1000);
            }
        };
        handler.post(runnable);
    }
    private void prepareCheckButton() {
        checkButton
                .setOnClickListener(v -> signUpCheckOtp());
    }
    private void signUpCheckOtp() {
        ApiService
                .getAccountApi(this)
                .signUpCheckOtp(SignUpCheckOtp.builder()
                        .sessionId(sessionId)
                        .otp(otpEditText.getText().toString())
                        .build())
                .enqueue(new Callback<>() {
                    @Override
                    public void onResponse(@NonNull Call<VerifyEmailOtpCheckResult> call, @NonNull Response<VerifyEmailOtpCheckResult> response) {
                        assert response.body() != null;
                        isOtpOk = response.body().isValid();
                        postSignUpCheckOtp();
                    }
                    @Override
                    public void onFailure(@NonNull Call<VerifyEmailOtpCheckResult> call, @NonNull Throwable throwable) {
                        Log.d(TAG, "signUpCheckOtp: onFailure: " + throwable);
                    }
                });
    }
    private void postSignUpCheckOtp() {
        if (!isOtpOk) {
            Toast.makeText(this, "Please recheck your OTP", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(SignUpStepTwoActivity.this, SignUpStepThreeActivity.class);
        intent.putExtra(IntentKey.SIGN_UP_EMAIL.name(), email);
        intent.putExtra(IntentKey.SIGN_UP_SESSION_ID.name(), sessionId);
        startActivity(intent);
    }
    /**
     * reset TextView: timeRemaining, resendOtp
     * prepare info not first time
     * get new sessionId and new otpDueDate
     */
    private void resendOtpClicked() {
        timeRemainingTextView.setText(null);
        timeRemainingTextView.setTextColor(getResources().getColor(R.color.black));
        resendOtpTextView.setText(null);
        resendOtpTextView.setOnClickListener(null);
        prepareInfo(false);
        stopCheckButton();
        signUpBegin(email);
    }
    @SuppressLint("SetTextI18n")
    private void stopCheckButton() {
        checkButton.setOnClickListener(null);
        String s = "Please wait";
        waitForResendOtp = true;
        checkOtpHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!waitForResendOtp) return;
                dotCount = (dotCount + 1) % 4;
                String text = s;
                text = text + new String(new char[dotCount]).replace("\0", ".");
                checkButton.setText(text);
                checkOtpHandler.postDelayed(this, 500);
            }
        }, 500);
    }
    /*
     * this calls postSignUpBegin
     */
    private void signUpBegin(String email) {
        sessionId = UUID.randomUUID().toString() + "-" + email;
        ApiService
                .getAccountApi(this)
                .signUpBegin(SignUpBegin
                        .builder()
                        .email(email)
                        .sessionId(sessionId)
                        .build())
                .enqueue(new Callback<>() {
                    @Override
                    public void onResponse(@NonNull Call<VerifyEmailDueDate> call, @NonNull Response<VerifyEmailDueDate> response) {
                        if (response.code() == 200) {
                            assert response.body() != null;
                            otpDueDate = Instant.parse(response.body().getOtpDueDate());
                            postSignUpBegin();
                        }
                    }
                    @Override
                    public void onFailure(@NonNull Call<VerifyEmailDueDate> call, @NonNull Throwable throwable) {
                        Log.d(TAG, "signUpBegin onFailure: " + throwable);
                    }
                });
    }
    @SuppressLint("SetTextI18n")
    private void resumeCheckButton() {
        waitForResendOtp = false;
        checkButton.setText("Check");
        prepareCheckButton();
    }
    private void postSignUpBegin() {
        resumeCheckButton();
        prepareTimeRemaining();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        waitForResendOtp = false;
        checkOtpHandler.removeCallbacksAndMessages(null);
    }
}