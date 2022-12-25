package com.example.projectandroid.User.MShopping.ListPromotion;

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
import com.example.projectandroid.User.MShopping.ListBill.DetailBill;
import com.example.projectandroid.User.MShopping.ListBill.UpdateBill;
import com.example.projectandroid.User.Shopping;

public class DetailPromotion extends AppCompatActivity {

    TextView typeProduct, nameProduct, priceProduct, priceAfter, percentPromotion, startDayPromotion, endDayPromotion;
    ImageView imageProduct,btnBack;

    SqlDatabaseHelper db;
    int id_Promotion;

    Toolbar toolbar;

    Button ConfirmBtnDia, CancelBtnDia;
    TextView ContentDia;
    Dialog dialog;

    ProgessLoading progessLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_detail_promotion);
        
        db = new SqlDatabaseHelper(this);

        progessLoading = new ProgessLoading(this);

        typeProduct = findViewById(R.id.detail_promotion_typeProduct);
        nameProduct = findViewById(R.id.detail_promotion_nameProduct);
        priceProduct = findViewById(R.id.detail_promotion_priceBefore);
        percentPromotion = findViewById(R.id.detail_promotion_percent);
        priceAfter = findViewById(R.id.detail_promotion_priceAfter);
        startDayPromotion = findViewById(R.id.detail_promotion_startDay);
        endDayPromotion = findViewById(R.id.detail_promotion_endDay);
        imageProduct = findViewById(R.id.detail_promotion_imageProduct);
        btnBack = findViewById(R.id.back_btn);
        toolbar = findViewById(R.id.detail_promotion_toolBar);
        toolbar.inflateMenu(R.menu.option_menu);

        Intent i = getIntent();
        Integer id = i.getIntExtra("Id_Promotion",0);
        id_Promotion = id;

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {

                    case R.id.delete:
                        ContentDia.setText("Bạn Có Chắc Chắn Muốn Xóa Không?!");
                        dialog.show();
                        break;

                    case R.id.update:
                        Intent intent = new Intent(DetailPromotion.this, UpdatePromotion.class);
                        intent.putExtra("Id_Promotion", id);
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

    private void readAllData() {

        Cursor cursor = db.readAllData_Promotion(id_Promotion);
        if(cursor.getCount() == 0){
            Toast.makeText(this, "Không Có Khuyến Mãi", Toast.LENGTH_SHORT).show();
        }else{
            while (cursor.moveToNext()){
                typeProduct.setText(cursor.getString(1));
                nameProduct.setText(cursor.getString(2));
                priceProduct.setText(cursor.getString(3));
                percentPromotion.setText(cursor.getString(4) + "%");
                priceAfter.setText(cursor.getString(5));
                startDayPromotion.setText(cursor.getString(6));
                endDayPromotion.setText(cursor.getString(7));
                byte[] image = cursor.getBlob(8);
                Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
                imageProduct.setImageBitmap(bitmap);
            }
        }
    }

    public void ShowDiaLog() {

        dialog = new Dialog(DetailPromotion.this);
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
                Boolean resultDeleteData = db.deleteData_Promotion(id_Promotion);
                if (resultDeleteData == true){
                    dialog.dismiss();
                    progessLoading.show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(getApplicationContext(), Shopping.class);
                            startActivity(intent);
                            progessLoading.dismiss();
                            Toast.makeText(getApplicationContext(), "Xóa Khuyến Mãi Thành Công", Toast.LENGTH_SHORT).show();
                        }
                    }, 2000);

                }else{
                    Toast.makeText(getApplicationContext(), "Xóa Khuyến Mãi Thất Bại", Toast.LENGTH_SHORT).show();
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
                DetailPromotion.super.onBackPressed();
            }
        });

    }
}