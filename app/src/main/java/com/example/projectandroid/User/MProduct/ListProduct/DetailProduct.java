package com.example.projectandroid.User.MProduct.ListProduct;

import static com.example.projectandroid.User.DashBoard.idUser;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectandroid.HelperClasses.SqlLite.SqlDatabaseHelper;
import com.example.projectandroid.ProgessLoading;
import com.example.projectandroid.R;
import com.example.projectandroid.User.Product;

public class DetailProduct extends AppCompatActivity {

    TextView nameProduct, qualityProduct, typeProduct, unitProduct, priceProduct;
    ImageView imageProduct, btnBack;

    SqlDatabaseHelper db;
    int id_Product;

    Toolbar toolbar;

    Button ConfirmBtnDia, CancelBtnDia;
    TextView ContentDia;
    Dialog dialog;

    ProgessLoading progessLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_detail_product);

        db = new SqlDatabaseHelper(this);

        progessLoading = new ProgessLoading(this);

        nameProduct = findViewById(R.id.detail_product_name);
        qualityProduct = findViewById(R.id.detail_product_quality);
        imageProduct = findViewById(R.id.detail_product_image);
        typeProduct = findViewById(R.id.detail_product_type);
        unitProduct = findViewById(R.id.detail_product_unit);
        priceProduct = findViewById(R.id.detail_product_price);
        btnBack = findViewById(R.id.back_btn);
        toolbar = findViewById(R.id.detail_product_toolBar);
        toolbar.inflateMenu(R.menu.option_menu);

        Intent i = getIntent();
        String id = i.getStringExtra("Id_Product");
        id_Product = Integer.parseInt(id);

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){

                    case R.id.delete:
                        ContentDia.setText("Bạn Có Chắc Chắn Muốn Xóa Không?!\n Tất cả Hóa Đơn Và Khuyến Mãi Đều Bị Xóa");
                        dialog.show();
                        break;

                    case R.id.update:
                        Intent intent = new Intent(DetailProduct.this, UpdateProduct.class);
                        intent.putExtra("Id_Product", id);
                        startActivity(intent);
                        break;

                }
                return true;
            }
        });

        readAllData();
        ShowDiaLog();
        btnBack();
    }

    private void readAllData() {
        Cursor cursor = db.ReadAllData_Product(id_Product);
        if(cursor.getCount() == 0){
            Toast.makeText(this, "Không Có Id Sản Phẩm", Toast.LENGTH_SHORT).show();
        }else{
            while (cursor.moveToNext()){
                typeProduct.setText(cursor.getString(1));
                nameProduct.setText(cursor.getString(2));
                qualityProduct.setText(cursor.getString(3));
                unitProduct.setText(cursor.getString(4));
                priceProduct.setText(cursor.getString(5));
                byte[] image = cursor.getBlob(6);
                Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
                imageProduct.setImageBitmap(bitmap);
            }

        }
    }

    public void ShowDiaLog() {

        dialog = new Dialog(DetailProduct.this);
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
                Boolean resultDeleteData = db.deleteData_Product(id_Product);
                if (resultDeleteData == true){
                    dialog.dismiss();
                    progessLoading.show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(getApplicationContext(), Product.class);
                            startActivity(intent);
                            progessLoading.dismiss();
                            Toast.makeText(getApplicationContext(), "Xóa Sản Phẩm Thành Công", Toast.LENGTH_SHORT).show();
                        }
                    }, 2000);

                }else{
                    Toast.makeText(getApplicationContext(), "Xóa Sản Phẩm Thất Bại", Toast.LENGTH_SHORT).show();
                }
            }
        });

        CancelBtnDia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

    }

    private void btnBack() {

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DetailProduct.super.onBackPressed();
            }
        });

    }
}