package com.example.projectandroid.User.MProduct.AddProduct;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.projectandroid.R;
import com.example.projectandroid.User.DashBoard;
import com.example.projectandroid.User.Product;

public class CompleteAddProduct extends AppCompatActivity {

    Button backtoDashboard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_complete_add_product);

        backtoDashboard = findViewById(R.id.back_to_Dashboard);

        backtoDashboard();
    }

    private void backtoDashboard() {

        backtoDashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Product.class);
                startActivity(intent);
            }
        });
    }
}