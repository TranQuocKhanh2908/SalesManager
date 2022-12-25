package com.example.projectandroid.common.LoginSignUp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.projectandroid.R;
import com.google.android.material.textfield.TextInputEditText;

public class CompleteSignUp extends AppCompatActivity {

    Button backToLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_sign_up);

        backToLogin = findViewById(R.id.back_to_login_btn);

        backToLogin();
    }

    private void backToLogin() {
        backToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),Login.class);
                startActivity(intent);
            }
        });
    }
}