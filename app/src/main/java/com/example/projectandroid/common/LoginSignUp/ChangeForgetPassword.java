package com.example.projectandroid.common.LoginSignUp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.projectandroid.HelperClasses.SqlLite.SqlDatabaseHelper;
import com.example.projectandroid.ProgessLoading;
import com.example.projectandroid.R;
import com.google.android.material.textfield.TextInputEditText;

public class ChangeForgetPassword extends AppCompatActivity {

    TextInputEditText newPass, rePass;
    Button confirmBtn;

    SqlDatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_forget_password);

        db = new SqlDatabaseHelper(this);

        final ProgessLoading progessLoading = new ProgessLoading(ChangeForgetPassword.this);

        newPass = findViewById(R.id.new_Password);
        rePass = findViewById(R.id.re_Password);
        confirmBtn = findViewById(R.id.confirm_btn);

        Intent i = getIntent();
        String gEmail = i.getStringExtra("email");


        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String gNewPass = newPass.getText().toString();
                String gRePass = rePass.getText().toString();

                if (gNewPass.isEmpty() || gRePass.isEmpty()) {

                    Toast.makeText(ChangeForgetPassword.this, "Vui Lòng Nhập Đầy Đủ Thông Tin", Toast.LENGTH_SHORT).show();

                } else {

                    if (gNewPass.equals(gRePass)) {

                        Boolean resultUpdatePassword = db.changeForgetPassword_Users(gEmail, gNewPass);
                        if (resultUpdatePassword == true) {

                            progessLoading.show();

                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Intent intent = new Intent(ChangeForgetPassword.this, CompleteForgetPassword.class);
                                    startActivity(intent);
                                    progessLoading.dismiss();
                                }
                            }, 2000);

                        } else {

                            progessLoading.show();

                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(ChangeForgetPassword.this, "Sửa Mật Khẩu Thất Bại", Toast.LENGTH_SHORT).show();
                                    progessLoading.dismiss();
                                }
                            }, 2000);

                        }
                    } else {

                        Toast.makeText(ChangeForgetPassword.this, "Vui Lòng Nhập Hai Mật Khẩu Giống Nhau", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });

    }
}