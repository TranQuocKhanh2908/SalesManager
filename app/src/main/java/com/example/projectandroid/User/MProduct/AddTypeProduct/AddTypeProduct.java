package com.example.projectandroid.User.MProduct.AddTypeProduct;

import static com.example.projectandroid.User.DashBoard.idUser;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.projectandroid.HelperClasses.SqlLite.SqlDatabaseHelper;
import com.example.projectandroid.ProgessLoading;
import com.example.projectandroid.R;
import com.example.projectandroid.SessionManager;
import com.example.projectandroid.User.Product;
import com.google.android.material.textfield.TextInputEditText;

public class AddTypeProduct extends AppCompatActivity {

    TextInputEditText nameTypeProduct, descTypeProduct;
    Button confirmBtn;
    ImageView backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_add_type_product);

        SqlDatabaseHelper db = new SqlDatabaseHelper(this);

        final ProgessLoading progessLoading = new ProgessLoading(this);


        confirmBtn = findViewById(R.id.confirm_btn_addTypeProduct);
        nameTypeProduct = findViewById(R.id.name_typeProduct_typeProduct);
        descTypeProduct = findViewById(R.id.desc_typeProduct_Product);
        backBtn = findViewById(R.id.back_btn);


        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String gName = nameTypeProduct.getText().toString();
                String gDesc = descTypeProduct.getText().toString();

                if(gName.isEmpty() || gDesc.isEmpty()){

                    Toast.makeText(AddTypeProduct.this, "Vui Lòng Nhập Đầy Đủ Thông Tin", Toast.LENGTH_SHORT).show();

                }else{

                    Boolean resultNameTypeProduct = db.checkNameTypeProduct_TypeProduct(gName, Integer.valueOf(idUser));
                    if(resultNameTypeProduct == false){

                        Boolean resultInsertData = db.insertData_TypeProduct(gName,gDesc, Integer.valueOf(idUser));
                        if (resultInsertData  == true){

                            progessLoading.show();
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(AddTypeProduct.this, "Thêm Loại Sản Phẩm Thành Công", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), Product.class);
                                    startActivity(intent);
                                    progessLoading.dismiss();
                                }
                            },2000);

                        }else{

                            progessLoading.show();
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(AddTypeProduct.this, "Thêm Loại Sản Phẩm Thất Bại", Toast.LENGTH_SHORT).show();
                                    progessLoading.dismiss();
                                }
                            },2000);

                        }

                    }else{

                        progessLoading.show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(AddTypeProduct.this, "Tên Loại Sản Phẩm Đã Tồn Tại", Toast.LENGTH_SHORT).show();
                                progessLoading.dismiss();
                                nameTypeProduct.forceLayout();
                            }
                        },2000);

                    }

                }
            }
        });

        backBtn();
    }

    private void backBtn() {

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddTypeProduct.super.onBackPressed();
            }
        });

    }
}