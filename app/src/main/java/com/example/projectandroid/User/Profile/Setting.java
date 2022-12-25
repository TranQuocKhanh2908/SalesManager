package com.example.projectandroid.User.Profile;

import static androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG;
import static androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricManager;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.projectandroid.R;
import com.example.projectandroid.SessionManager;
import com.example.projectandroid.User.DashBoard;
import com.example.projectandroid.common.LoginSignUp.Login;
import com.google.android.material.switchmaterial.SwitchMaterial;

import java.util.concurrent.Executor;

public class Setting extends AppCompatActivity {

    private static final int REQUEST_CODE = 3;
    SwitchMaterial verifyBio;
    ImageView backBtn;

    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        sessionManager = new SessionManager(this);

        verifyBio = findViewById(R.id.sw_VerifyBio);
        backBtn = findViewById(R.id.back_btn);

        verifyBio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (verifyBio.isChecked()) {
                    sessionManager.setBio(false);
                    verifyBio.setChecked(false);

                    Boolean resultCheckBio = checkSPBiometric();
                    if (resultCheckBio == true) {

                        Executor executor = ContextCompat.getMainExecutor(Setting.this);
                        androidx.biometric.BiometricPrompt biometricPrompt = new androidx.biometric.BiometricPrompt(Setting.this, executor, new androidx.biometric.BiometricPrompt.AuthenticationCallback() {
                            @Override
                            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                                super.onAuthenticationError(errorCode, errString);
                            }

                            @Override
                            public void onAuthenticationSucceeded(@NonNull androidx.biometric.BiometricPrompt.AuthenticationResult result) {
                                Toast.makeText(Setting.this, "Bật Xác Thực Sinh Trắc Học Thành Công", Toast.LENGTH_SHORT).show();
                                sessionManager.setBio(true);
                                verifyBio.setChecked(true);
                            }

                            @Override
                            public void onAuthenticationFailed() {
                                super.onAuthenticationFailed();
                                Toast.makeText(Setting.this, "Vân Tay Hoặc Khuông Mặt Không Khớp", Toast.LENGTH_SHORT).show();
                                sessionManager.setBio(false);
                                verifyBio.setChecked(false);
                            }
                        });
                        androidx.biometric.BiometricPrompt.PromptInfo.Builder promptInfo = new androidx.biometric.BiometricPrompt.PromptInfo.Builder()
                                .setTitle("Xác Thực Vân Tay Hoặc Khuông Mặt")
                                .setNegativeButtonText("Hủy");
                        biometricPrompt.authenticate(promptInfo.build());

                    } else {
                        sessionManager.setBio(false);
                        verifyBio.setChecked(false);
                    }
                } else {

                    sessionManager.setBio(false);
                    verifyBio.setChecked(false);

                }
            }
        });

        if(sessionManager.isTurnOnBio()){
            verifyBio.setChecked(true);
        }else{
            verifyBio.setChecked(false);
        }

        backBtn();
    }

    private void backBtn() {

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Setting.super.onBackPressed();
            }
        });
    }

    private boolean checkSPBiometric() {
        BiometricManager biometricManager = BiometricManager.from(Setting.this);
        switch (biometricManager.canAuthenticate(BIOMETRIC_STRONG | DEVICE_CREDENTIAL)) {
            case BiometricManager.BIOMETRIC_SUCCESS:
                Log.d("MY_APP_TAG", "App can authenticate using biometrics.");
                return true;
            case BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE:
                Toast.makeText(Setting.this, "Thiết Bị của Bạn Không Hỗ Trợ Cảm Biến Vân Tay", Toast.LENGTH_SHORT).show();
                return false;
            case BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE:
                Toast.makeText(Setting.this, "Thiết Bị của Bạn Không Hỗ Trợ Cảm Biến Vân Tay Hoặc Đang Lỗi", Toast.LENGTH_SHORT).show();
                return false;
            case BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED:
                // Prompts the user to create credentials that your app accepts.
                final Intent enrollIntent = new Intent(Settings.ACTION_BIOMETRIC_ENROLL);
                enrollIntent.putExtra(Settings.EXTRA_BIOMETRIC_AUTHENTICATORS_ALLOWED,
                        BIOMETRIC_STRONG | DEVICE_CREDENTIAL);
                startActivityForResult(enrollIntent, REQUEST_CODE);
                return false;
        }
        return false;
    }
}