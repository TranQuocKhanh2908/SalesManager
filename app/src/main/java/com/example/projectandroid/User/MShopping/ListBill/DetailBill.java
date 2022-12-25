package com.example.projectandroid.User.MShopping.ListBill;

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
import com.example.projectandroid.User.MProduct.ListProduct.UpdateProduct;
import com.example.projectandroid.User.Shopping;

public class DetailBill extends AppCompatActivity {

    TextView typeProduct, nameProduct, qualityProduct, priceProduct, totalPriceBill, createDayBill, createTimeBill ;
    ImageView imageBill,btnBack;

    SqlDatabaseHelper db;
    int id_Bill;

    Toolbar toolbar;

    Button ConfirmBtnDia, CancelBtnDia;
    TextView ContentDia;
    Dialog dialog;

    ProgessLoading progessLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_detail_bill);

        db = new SqlDatabaseHelper(this);

        progessLoading = new ProgessLoading(this);

        typeProduct = findViewById(R.id.detail_bill_typeProduct);
        nameProduct = findViewById(R.id.detail_bill_nameProduct);
        qualityProduct = findViewById(R.id.detail_bill_qualityProduct);
        priceProduct = findViewById(R.id.detail_bill_priceProduct);
        totalPriceBill = findViewById(R.id.detail_bill_TotalPriceBill);
        createDayBill = findViewById(R.id.detail_bill_CreateDayBill);
        createTimeBill = findViewById(R.id.detail_bill_CreateTimeBill);
        imageBill = findViewById(R.id.detail_bill_imageProduct);
        btnBack = findViewById(R.id.back_btn);
        toolbar = findViewById(R.id.detail_bill_toolBar);
        toolbar.inflateMenu(R.menu.option_menu);
        Intent i = getIntent();
        Integer id = i.getIntExtra("Id_Bill",0);
        id_Bill = id;

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){

                    case R.id.delete:
                        ContentDia.setText("Bạn Có Chắc Chắn Muốn Xóa Không?!");
                        dialog.show();
                        break;

                    case R.id.update:
                        Intent intent = new Intent(DetailBill.this, UpdateBill.class);
                        intent.putExtra("Id_Bill", id);
                        startActivity(intent);
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

        dialog = new Dialog(DetailBill.this);
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
                Boolean resultDeleteData = db.deleteData_Bill(id_Bill);
                if (resultDeleteData == true){
                    dialog.dismiss();
                    progessLoading.show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(getApplicationContext(), Shopping.class);
                            startActivity(intent);
                            progessLoading.dismiss();
                            Toast.makeText(getApplicationContext(), "Xóa Hóa Đơn Thành Công", Toast.LENGTH_SHORT).show();
                        }
                    }, 2000);

                }else{
                    Toast.makeText(getApplicationContext(), "Xóa Hóa Đơn Thất Bại", Toast.LENGTH_SHORT).show();
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

        String a = null;
        String b = null;
        Cursor cursor = db.readAllData_Bill(id_Bill);
        if (cursor.getCount() == 0){
            Toast.makeText(this, "Không Có Hóa Đơn", Toast.LENGTH_SHORT).show();
        }else{
            while (cursor.moveToNext()){
                typeProduct.setText(cursor.getString(1));
                nameProduct.setText(cursor.getString(2));
                priceProduct.setText(String.valueOf(Float.parseFloat(cursor.getString(5)) / Float.parseFloat(cursor.getString(4))));
                qualityProduct.setText(cursor.getString(4));
                totalPriceBill.setText(cursor.getString(5));
                createDayBill.setText(cursor.getString(6));
                createTimeBill.setText(cursor.getString(7));
                byte[] image = cursor.getBlob(8);
                Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
                imageBill.setImageBitmap(bitmap);
            }
        }

    }

    private void btnBack() {

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DetailBill.super.onBackPressed();
            }
        });

    }
}