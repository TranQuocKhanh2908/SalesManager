package com.example.projectandroid.User.MProduct.ListHistoryImportProduct;

import androidx.appcompat.app.AppCompatActivity;
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
import com.example.projectandroid.User.MShopping.ListBill.DetailBill;
import com.example.projectandroid.User.MShopping.ListBill.UpdateBill;
import com.example.projectandroid.User.Product;
import com.example.projectandroid.User.Shopping;

public class DetailHistoryImportProduct extends AppCompatActivity {

    TextView typeProduct, nameProduct, oldQualityProduct, newQualityProduct, priceProduct, totalPriceImportProduct, createDayImportProduct, createTimeImportProduct;
    ImageView imageImportProduct,btnBack;

    SqlDatabaseHelper db;
    int id_Import_Product;

    Toolbar toolbar;

    Button ConfirmBtnDia, CancelBtnDia;
    TextView ContentDia;
    Dialog dialog;

    ProgessLoading progessLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_history_import_product);

        db = new SqlDatabaseHelper(this);

        progessLoading = new ProgessLoading(this);

        typeProduct = findViewById(R.id.detail_import_product_typeProduct);
        nameProduct = findViewById(R.id.detail_import_product_nameProduct);
        oldQualityProduct = findViewById(R.id.detail_import_product_oldQualityProduct);
        newQualityProduct = findViewById(R.id.detail_import_product_newQualityProduct);
        priceProduct = findViewById(R.id.detail_import_product_priceProduct);
        totalPriceImportProduct = findViewById(R.id.detail_import_product_TotalPrice);
        createDayImportProduct = findViewById(R.id.detail_import_product_CreateDay);
        createTimeImportProduct = findViewById(R.id.detail_import_product_CreateTime);
        imageImportProduct = findViewById(R.id.detail_import_product_imageProduct);
        btnBack = findViewById(R.id.back_btn);
        toolbar = findViewById(R.id.detail_import_product_toolBar);
        toolbar.inflateMenu(R.menu.option_menu_import_product);
        Intent i = getIntent();
        Integer id = i.getIntExtra("Id_Import_Product",0);
        id_Import_Product = id;

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){

                    case R.id.delete:
                        ContentDia.setText("Bạn Có Chắc Chắn Muốn Xóa Không?!");
                        dialog.show();
                        break;
                }
                return true;
            }
        });

        readAllData();
        btnBack();
        ShowDiaLog();
    }

    public void ShowDiaLog() {

        dialog = new Dialog(DetailHistoryImportProduct.this);
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
                Boolean resultDeleteData = db.deleteData_ImportProduct(id_Import_Product);
                if (resultDeleteData == true){
                    dialog.dismiss();
                    progessLoading.show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(getApplicationContext(), Product.class);
                            startActivity(intent);
                            progessLoading.dismiss();
                            Toast.makeText(getApplicationContext(), "Xóa Lịch Sử Nhập Hàng Thành Công", Toast.LENGTH_SHORT).show();
                        }
                    }, 2000);

                }else{
                    Toast.makeText(getApplicationContext(), "Xóa Lịch Sử Nhập Hàng Thất Bại", Toast.LENGTH_SHORT).show();
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

    private void readAllData() {

        Cursor cursor = db.readAllData_ImportProduct(id_Import_Product);
        if (cursor.getCount() == 0){
            Toast.makeText(this, "Không Có Lịch Sự Nhập Hàng", Toast.LENGTH_SHORT).show();
        }else{
            while (cursor.moveToNext()){
                typeProduct.setText(cursor.getString(1));
                nameProduct.setText(cursor.getString(2));
                priceProduct.setText(cursor.getString(3));
                totalPriceImportProduct.setText(cursor.getString(4));
                oldQualityProduct.setText(cursor.getString(5));
                newQualityProduct.setText(cursor.getString(6));
                createDayImportProduct.setText(cursor.getString(7));
                createTimeImportProduct.setText(cursor.getString(8));
                byte[] image = cursor.getBlob(9);
                Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
                imageImportProduct.setImageBitmap(bitmap);
            }
        }

    }

    private void btnBack() {

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DetailHistoryImportProduct.super.onBackPressed();
            }
        });

    }
}