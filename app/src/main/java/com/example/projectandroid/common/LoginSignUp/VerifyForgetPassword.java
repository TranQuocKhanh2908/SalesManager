
package com.example.projectandroid.common.LoginSignUp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.projectandroid.HelperClasses.SqlLite.SqlDatabaseHelper;
import com.example.projectandroid.ProgessLoading;
import com.example.projectandroid.R;
import com.example.projectandroid.User.Profile.ChangePassword;
import com.google.android.material.textfield.TextInputEditText;

public class VerifyForgetPassword extends AppCompatActivity {

    TextInputEditText pCode1, pCode2, pCode3, pCode4;
    Button confirmBtn;

    String gEmail;
    Integer verifyCode;

    SqlDatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_forget_password);

        db = new SqlDatabaseHelper(this);

        final ProgessLoading progessLoading = new ProgessLoading(VerifyForgetPassword.this);

        pCode1 = findViewById(R.id.Code_1);
        pCode2 = findViewById(R.id.Code_2);
        pCode3 = findViewById(R.id.Code_3);
        pCode4 = findViewById(R.id.Code_4);
        confirmBtn = findViewById(R.id.confirm_btn);

        Intent i = getIntent();
        gEmail = i.getStringExtra("email");
        verifyCode = i.getIntExtra("code",0);

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Boolean resultCheckCode = checkCode(verifyCode);
                if (resultCheckCode == true) {

                    progessLoading.show();

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(VerifyForgetPassword.this, ChangeForgetPassword.class);
                            intent.putExtra("email",gEmail);
                            startActivity(intent);
                            progessLoading.dismiss();
                            finish();
                        }
                    }, 2000);
                } else {

                    Toast.makeText(VerifyForgetPassword.this, "Sai Mã, Vui Lòng Nhập Lại", Toast.LENGTH_SHORT).show();
                    pCode1.setText("");
                    pCode2.setText("");
                    pCode3.setText("");
                    pCode4.setText("");

                }
            }
        });

        nextCode();
    }

    private void nextCode() {
        pCode1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() > 0) {
                    pCode2.requestFocus();
                }
            }
        });

        pCode2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() > 0) {
                    pCode3.requestFocus();
                }
            }
        });

        pCode3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() > 0) {
                    pCode4.requestFocus();
                }
            }
        });

        pCode4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() > 0) {
                    confirmBtn.requestFocus();
                }
            }
        });
    }

    private boolean checkCode(Integer code){

        String inputCode = pCode1.getText().toString() + pCode2.getText().toString() + pCode3.getText().toString() + pCode4.getText().toString();
        if (code.equals(Integer.parseInt(inputCode))){
            return true;
        }else{
            return false;
        }
    }
}