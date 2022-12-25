package com.example.projectandroid.User.MProduct.ImportProduct;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.projectandroid.R;
import com.example.projectandroid.User.Product;

public class CompleteImportProduct extends AppCompatActivity {

    Button backtoDashboard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_import_product);

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