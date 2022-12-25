package com.example.projectandroid.common.LoginSignUp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectandroid.HelperClasses.SqlLite.SqlDatabaseHelper;
import com.example.projectandroid.MainActivity;
import com.example.projectandroid.R;
import com.example.projectandroid.SessionManager;
import com.example.projectandroid.User.DashBoard;
import com.google.android.material.switchmaterial.SwitchMaterial;

public class StartUpScreen extends MainActivity {

    private static final int REQUEST_CODE = 1;
    ImageView imageStart;
    Button btnLogin, btnSignUp;
    TextView textViewWE, textViewDE;

    SessionManager sessionManager;

    String idUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_start_up_screen);

        sessionManager = new SessionManager(this);

        idUser = sessionManager.getID();

        if (sessionManager.isLoggedIn()){
            Intent intent = new Intent(StartUpScreen.this, DashBoard.class);
            startActivity(intent);
            finish();
        }else{

            if (idUser.equals("0") || idUser.equals("")) {


            }else{

                Intent intent = new Intent(StartUpScreen.this, Login.class);
                startActivity(intent);
                finish();

            }

        }


        imageStart = findViewById(R.id.imageStart);
        btnLogin = findViewById(R.id.login_btn);
        btnSignUp = findViewById(R.id.signup_btn);
        textViewDE = findViewById(R.id.textviewDE);
        textViewWE = findViewById(R.id.textViewWE);

        imageStart.setTranslationX(1000);
        btnLogin.setTranslationY(300);
        btnSignUp.setTranslationY(300);
        textViewDE.setTranslationX(1000);
        textViewWE.setTranslationX(1000);

        imageStart.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(700).start();
        btnLogin.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(1500).start();
        btnSignUp.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(1500).start();
        textViewDE.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(900).start();
        textViewWE.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(800).start();

        checkPermission();
    }

    private void checkPermission() {

        if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                && checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){

        }else{
            String[] Permission = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
            requestPermissions(Permission, REQUEST_CODE);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    public void callLoginSrceen(View view){
        Intent intent = new Intent(getApplicationContext(),Login.class);
        startActivity(intent);
    }

    public void callSignUpSrceen(View view){
        Intent intent = new Intent(getApplicationContext(),SignUp.class);
        startActivity(intent);
    }
}