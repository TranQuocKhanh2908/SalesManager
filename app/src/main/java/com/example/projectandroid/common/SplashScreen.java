package com.example.projectandroid.common;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.projectandroid.MainActivity;
import com.example.projectandroid.R;
import com.example.projectandroid.SessionManager;
import com.example.projectandroid.common.LoginSignUp.StartUpScreen;

public class SplashScreen extends AppCompatActivity {

    private static int Splash_Timer = 5000;

    ImageView backgroundImage;
    TextView poweredBy, textTitle;

    Animation sideAnim, bottomAnim;

    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_splash_screen);

        sessionManager = new SessionManager(this);

        backgroundImage = findViewById(R.id.background_image);
        poweredBy = findViewById(R.id.powered_By);
        textTitle = findViewById(R.id.text_title);

        sideAnim = AnimationUtils.loadAnimation(this,R.anim.side_anim);
        bottomAnim = AnimationUtils.loadAnimation(this,R.anim.bottom_anim);

        backgroundImage.setAnimation(sideAnim);
        textTitle.setAnimation(sideAnim);
        poweredBy.setAnimation(bottomAnim);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if(sessionManager.isSecTime() == false){

                    Intent i=new Intent(SplashScreen.this,OnBoarding.class);
                    startActivity(i);
                    finish();

                }else{

                    Intent a=new Intent(SplashScreen.this,StartUpScreen.class);
                    startActivity(a);  // Launch next activity
                    finish();

                }

            }
        },Splash_Timer);
    }
}