package com.example.projectandroid.common.LoginSignUp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.PatternMatcher;
import android.util.Patterns;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class SignUp extends AppCompatActivity {

    public static int verifyCode;

    String[] itemGender = {"Nam", "Nữ", "Khác"};
    AutoCompleteTextView gender;
    ArrayAdapter<String> adapterItemGender;

    Button signup,login, ConfirmBtnDia, CancelBtnDia;
    TextView ContentDia;
    ImageView backbtn;
    TextInputEditText name, username, email, phone, password, rePassword;
    DatePicker age;

    SqlDatabaseHelper db;

    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_sign_up);

        db = new SqlDatabaseHelper(this);

        final ProgessLoading progessLoading = new ProgessLoading(SignUp.this);

        backbtn = findViewById(R.id.signup_back_button);
        login = findViewById(R.id.signup_login_button);
        signup = findViewById(R.id.sigun_button);
        name = findViewById(R.id.Name_signup);
        username = findViewById(R.id.UserNane_signup);
        email = findViewById(R.id.Email_signup);
        phone = findViewById(R.id.Phone_signup);
        password = findViewById(R.id.Password_signup);
        rePassword = findViewById(R.id.rePassword_signup);
        age = findViewById(R.id.Age_signup);
        gender = findViewById(R.id.Gender_signup);
        adapterItemGender = new ArrayAdapter<String>(this, R.layout.list_item_dropmenu, itemGender);
        gender.setAdapter(adapterItemGender);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String gName = name.getText().toString();
                String gUserName = username.getText().toString();
                String gPassword = password.getText().toString();
                String grePassword = rePassword.getText().toString();
                String gEmail = email.getText().toString();
                String gPhone = phone.getText().toString();
                String gGender = gender.getText().toString();

                Date gCurrentDay = null;
                Date gDATE = null;
                SimpleDateFormat dateFormat = new SimpleDateFormat("MM/d/yyyy");
                Calendar c = Calendar.getInstance();
                int Day = age.getDayOfMonth();
                int Month = age.getMonth();
                int Year = age.getYear();
                String gAge = Month + 1 + "/" + Day + "/" + Year;
                String gCDate = dateFormat.format(c.getTime());
                try {
                    gCurrentDay = dateFormat.parse(gCDate);
                    gDATE = dateFormat.parse(gAge);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                if (gName.isEmpty() || gUserName.isEmpty() || gPassword.isEmpty() || grePassword.isEmpty() || gEmail.isEmpty() || gPhone.isEmpty() || gGender.isEmpty() || gAge.isEmpty()) {

                    Toast.makeText(SignUp.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();

                } else {

                    if (Patterns.EMAIL_ADDRESS.matcher(gEmail).matches()) {

                        if (gDATE.before(gCurrentDay)) {

                            if (gPassword.equals(grePassword)) {

                                Boolean userCheckResult = db.checkUsername_Users(gUserName);
                                if (userCheckResult == true) {

                                    Toast.makeText(SignUp.this, "Tên đăng nhập đã tồn tại. \nVui lòng Đăng Nhập", Toast.LENGTH_LONG).show();
                                    username.forceLayout();

                                } else {

                                    Boolean emailCheckResult = db.checkEmail_Users(gEmail);
                                    if (emailCheckResult == true) {

                                        Toast.makeText(SignUp.this, "Email đã tồn tại. \nVui lòng Đăng Nhập", Toast.LENGTH_LONG).show();
                                        email.forceLayout();

                                    } else {

                                        Boolean phoneCheckResult = db.checkPhone_Users(gPhone);
                                        if (phoneCheckResult == true) {

                                            Toast.makeText(SignUp.this, "Số Điện Thoại đã tồn tại. \nVui lòng Đăng Nhập", Toast.LENGTH_LONG).show();
                                            phone.forceLayout();

                                        } else {

                                            progessLoading.show();

                                            sendVerifyEmail(gEmail);

                                            new Handler().postDelayed(new Runnable() {
                                                @Override
                                                public void run() {
                                                    Intent intent = new Intent(SignUp.this, VerifySignUp.class);
                                                    intent.putExtra("name", gName);
                                                    intent.putExtra("username", gUserName);
                                                    intent.putExtra("password", gPassword);
                                                    intent.putExtra("email", gEmail);
                                                    intent.putExtra("phone", gPhone);
                                                    intent.putExtra("gender", gGender);
                                                    intent.putExtra("age", gAge);
                                                    intent.putExtra("createDay", gCDate);
                                                    intent.putExtra("code", verifyCode);
                                                    startActivity(intent);
                                                    progessLoading.dismiss();
                                                }
                                            }, 2000);
                                        }
                                    }
                                }
                            } else {

                                Toast.makeText(SignUp.this, "Vui lòng nhập hai mật khẩu giống nhau", Toast.LENGTH_SHORT).show();
                            }
                        } else {

                            Toast.makeText(SignUp.this, "Vui Lòng Nhập Tuổi Hợp Lệ", Toast.LENGTH_SHORT).show();

                        }
                    } else {

                        Toast.makeText(SignUp.this, "Vui Lòng Nhập Đúng Đinh Dạng Email", Toast.LENGTH_SHORT).show();

                    }
                }
            }
        });

        backbtn();
        login();
        ShowDiaLog();
    }

    public void ShowDiaLog() {

        dialog = new Dialog(SignUp.this);
        dialog.setContentView(R.layout.custom_dialog);
        dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.bg_dialog));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);

        ConfirmBtnDia = dialog.findViewById(R.id.Confirm_dialog_btn);
        CancelBtnDia = dialog.findViewById(R.id.Cancel_dialog_btn);
        ContentDia = dialog.findViewById(R.id.tv_Content_dialog);

        ConfirmBtnDia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
                dialog.dismiss();
                finish();
            }
        });

        CancelBtnDia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

    }

    private void backbtn() {
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),StartUpScreen.class);
                startActivity(intent);
            }
        });
    }

    private void login() {
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),Login.class);
                startActivity(intent);
            }
        });
    }

    private void sendVerifyEmail(String email){
        Random random = new Random();
        verifyCode = random.nextInt(8999) + 1000;
        String url = "https://sendemailprojectandroid.000webhostapp.com/sendVerifyCodeSignUp.php";
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
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
}