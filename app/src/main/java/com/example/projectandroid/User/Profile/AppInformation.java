package com.example.projectandroid.User.Profile;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.projectandroid.R;

public class AppInformation extends AppCompatActivity {

    ImageView backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_information);

        backBtn = findViewById(R.id.back_btn);

        backBtn();
    }

    private void backBtn() {

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppInformation.super.onBackPressed();
            }
        });

    }
}