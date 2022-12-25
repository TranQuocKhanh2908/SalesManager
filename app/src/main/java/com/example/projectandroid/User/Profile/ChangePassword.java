package com.example.projectandroid.User.Profile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.database.Cursor;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.projectandroid.HelperClasses.SqlLite.SqlDatabaseHelper;
import com.example.projectandroid.ProgessLoading;
import com.example.projectandroid.R;
import com.example.projectandroid.SessionManager;
import com.google.android.material.textfield.TextInputEditText;

public class ChangePassword extends AppCompatActivity {

    ImageView btnBack;
    TextInputEditText EOldPassword, ENewPassword, EConfirmPassword;
    Button submitBtn;

    SqlDatabaseHelper db;
    SessionManager sessionManager;
    ProgessLoading progessLoading;

    String idUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_change_password);

        db = new SqlDatabaseHelper(this);
        progessLoading = new ProgessLoading(this);
        sessionManager = new SessionManager(this);


        btnBack = findViewById(R.id.back_btn);
        EOldPassword = findViewById(R.id.Txt_old_Password_Profile);
        ENewPassword = findViewById(R.id.Txt_new_Password_Profile);
        EConfirmPassword = findViewById(R.id.Txt_confirm_Password_Profile);
        submitBtn = findViewById(R.id.btn_Submit_Change_Password_profile);

        idUser = sessionManager.getID();

        submitBtn();
        btnBack();
    }

    public void CheckPassword(){

        String gOldPassword = EOldPassword.getText().toString();
        String gNewPassword = ENewPassword.getText().toString();
        String gRePassword = EConfirmPassword.getText().toString();

        if(gOldPassword.isEmpty() || gNewPassword.isEmpty() || gRePassword.isEmpty()) {

            Toast.makeText(this, "Vui Lòng Nhập Đầy Đủ Thông Tin", Toast.LENGTH_SHORT).show();

        }else {

            Boolean checkOldPassword = db.checkOldPassword_Users(Integer.valueOf(idUser), gOldPassword);
            if (checkOldPassword == true) {

                if (gNewPassword.equals(gRePassword)) {

                    Boolean resultUpdateData = db.updatePassword_Users(Integer.valueOf(idUser), gNewPassword);
                    if (resultUpdateData == true) {

                        progessLoading.show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = new Intent(ChangePassword.this, Profile.class);
                                startActivity(intent);
                                progessLoading.dismiss();
                                Toast.makeText(getApplicationContext(), "Đổi Mật Khẩu Thành Công", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }, 2000);

                    } else {

                        progessLoading.show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                progessLoading.dismiss();
                                Toast.makeText(getApplicationContext(), "Đổi Mật Khẩu Thất Bại", Toast.LENGTH_SHORT).show();
                            }
                        }, 2000);

                    }

                } else {

                    Toast.makeText(this, "Vui Lòng Nhập Hai Mật Khẩu Giống Nhau", Toast.LENGTH_SHORT).show();

                }

            } else {

                Toast.makeText(this, "Vui Lòng Nhập Đúng Mật Khẩu Cũ", Toast.LENGTH_SHORT).show();

            }
        }
    }

    private void submitBtn() {

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckPassword();
            }
        });

    }

    private void btnBack() {

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChangePassword.super.onBackPressed();
            }
        });
    }
}