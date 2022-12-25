package com.example.projectandroid.common.LoginSignUp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.projectandroid.HelperClasses.SqlLite.SqlDatabaseHelper;
import com.example.projectandroid.ProgessLoading;
import com.example.projectandroid.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class ForgetPassword extends AppCompatActivity {

    ImageView backbtn;
    Button confirmBtn;
    TextInputEditText Email;
    Integer verifyCode;
    SqlDatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_forget_password);

        db = new SqlDatabaseHelper(this);

        final ProgessLoading progessLoading = new ProgessLoading(this);

        backbtn = findViewById(R.id.forgetPassword_button_back);
        Email = findViewById(R.id.email_forgetPassword);
        confirmBtn = findViewById(R.id.confirm_btn);

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String gEmail = Email.getText().toString();
                if(gEmail.isEmpty()){

                    Toast.makeText(ForgetPassword.this, "Vui Lòng Nhập Emai", Toast.LENGTH_SHORT).show();

                }else{

                    if (Patterns.EMAIL_ADDRESS.matcher(gEmail).matches()) {


                        Boolean resultEmail = db.checkEmailExist_Users(gEmail);
                        if (resultEmail == true) {


                            progessLoading.show();

                            sendVerifyEmail(gEmail);

                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Intent intent = new Intent(ForgetPassword.this, VerifyForgetPassword.class);
                                    intent.putExtra("email", gEmail);
                                    intent.putExtra("code", verifyCode);
                                    startActivity(intent);
                                    progessLoading.dismiss();
                                }
                            }, 2000);
                        }else{

                            Toast.makeText(ForgetPassword.this, "Email Chưa Được Đăng Ký", Toast.LENGTH_SHORT).show();

                        }
                    }else{

                        Toast.makeText(ForgetPassword.this, "Vui Lòng Nhập Đúng Định Dạng Email", Toast.LENGTH_SHORT).show();

                    }
                }
            }
        });

        backbtn();
    }

    private void sendVerifyEmail(String email){
        Random random = new Random();
        verifyCode = random.nextInt(8999) + 1000;
        String url = "https://sendemailprojectandroid.000webhostapp.com/sendCodeForgetPassword.php";
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ForgetPassword.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("email",email);
                params.put("code", String.valueOf(verifyCode));
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void backbtn() {

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
            }
        });

    }

}