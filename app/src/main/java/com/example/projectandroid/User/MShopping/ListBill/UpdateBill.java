package com.example.projectandroid.User.MShopping.ListBill;

import static com.example.projectandroid.User.DashBoard.idUser;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.projectandroid.HelperClasses.SqlLite.SqlDatabaseHelper;
import com.example.projectandroid.ProgessLoading;
import com.example.projectandroid.R;
import com.example.projectandroid.User.MShopping.CreateBill.CreateBill;
import com.example.projectandroid.User.Shopping;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class UpdateBill extends AppCompatActivity {

    ArrayList<String> itemTypeProduct, itemProduct;
    AutoCompleteTextView TypeProduct,NameProduct;
    ArrayAdapter adapterItemTypeProduct, adapterItemProduct;

    ImageView btnBack, ImageProduct;
    Button btnConfirm;
    TextInputEditText QualityProduct, PriceProduct, TotalPrice, CreateDay, CreateTime;
    Integer Total;

    SqlDatabaseHelper db;

    int id_Bill;

    int getIDTypeProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_bill);

        db = new SqlDatabaseHelper(this);

        final ProgessLoading progessLoading = new ProgessLoading(this);

        btnBack = findViewById(R.id.back_btn);
        btnConfirm = findViewById(R.id.confirm_btn);
        TypeProduct = findViewById(R.id.type_product_updateBill);
        NameProduct = findViewById(R.id.name_product_updateBill);
        QualityProduct = findViewById(R.id.quality_product_updateBill);
        PriceProduct = findViewById(R.id.price_product_updateBill);
        TotalPrice = findViewById(R.id.total_price_updateBill);
        CreateDay = findViewById(R.id.create_day_updateBill);
        CreateTime = findViewById(R.id.create_time_updateBill);
        ImageProduct = findViewById(R.id.image_product_updateBill);

        Intent i = getIntent();
        Integer id = i.getIntExtra("Id_Bill",0);
        id_Bill = id;

        TypeProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Cursor cursor = db.getIDTypeProduct_Bill(TypeProduct.getText().toString(),Integer.valueOf(idUser));

                if(cursor.getCount() == 0){

                }else{
                    while (cursor.moveToNext()){
                        getIDTypeProduct = cursor.getInt(0);
                    }
                    loadDataNameProduct();
                    NameProduct.setText("");
                    PriceProduct.setText("");
                }
            }
        });

        TypeProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadDataTypeProduct();
            }
        });

        NameProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(TypeProduct.getText().toString())){
                    Toast.makeText(UpdateBill.this, "Vui Lòng Chọn Loại Sản Phẩm", Toast.LENGTH_SHORT).show();
                }else{
                    Cursor cursor = db.getIDTypeProduct_Bill(TypeProduct.getText().toString(),Integer.valueOf(idUser));

                    if(cursor.getCount() == 0){

                    }else{
                        while (cursor.moveToNext()){
                            getIDTypeProduct = cursor.getInt(0);
                        }
                        loadDataNameProduct();
                    }
                }
            }
        });

        NameProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                loadDataPriceProduct(NameProduct.getText().toString());
            }
        });

        QualityProduct.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(TextUtils.isEmpty(QualityProduct.getText().toString())){
                    Total = 0;
                }else{
                    Total = Integer.parseInt(PriceProduct.getText().toString()) * Integer.parseInt(QualityProduct.getText().toString());
                }
                TotalPrice.setText(Total.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String gTypeProduct = TypeProduct.getText().toString();

                Cursor cursorID_TypeProduct = db.getIDTypeProduct_Bill(gTypeProduct,Integer.valueOf(idUser));
                if (cursorID_TypeProduct.getCount() == 0) {

                    Toast.makeText(UpdateBill.this, "Vui Lòng Chọn Loại Sản Phẩm", Toast.LENGTH_SHORT).show();

                } else {

                    String gNameProduct = NameProduct.getText().toString();

                    Cursor cursorID_Product = db.getIDProduct_Bill(gNameProduct,Integer.valueOf(idUser));
                    if (cursorID_Product.getCount() == 0) {

                        Toast.makeText(UpdateBill.this, "Vui Lòng Chọn Sản Phẩm", Toast.LENGTH_SHORT).show();

                    } else {

                        Integer gIDTypeProduct = null;
                        Integer gIDProduct = null;
                        String gCQuality = null;
                        String gCQualityBill = null;
                        Integer gNewUpdateQuality = null;
                        Integer gNowQuality = null;
                        String gProductQuality = QualityProduct.getText().toString();
                        String gTotalPrice = Total.toString();

                        Date dateAndTime = Calendar.getInstance().getTime();
                        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());
                        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss", Locale.UK);
                        String gCreateDay = dateFormat.format(dateAndTime);
                        String gCreateTime = timeFormat.format(dateAndTime);

                        while (cursorID_TypeProduct.moveToNext()) {
                            gIDTypeProduct = cursorID_TypeProduct.getInt(0);
                        }
                        while (cursorID_Product.moveToNext()) {
                            gIDProduct = cursorID_Product.getInt(0);
                        }

                        Cursor cursorProduct_Quality = db.getQualityProduct_Bill(gIDProduct);
                        while (cursorProduct_Quality.moveToNext()) {
                            gCQuality = cursorProduct_Quality.getString(0);
                        }

                        Cursor cursorProductQuality_Bill = db.getQualityProductBill_Bill(id_Bill);
                        while (cursorProductQuality_Bill.moveToNext()) {
                            gCQualityBill = cursorProductQuality_Bill.getString(0);
                        }

                        if(Integer.parseInt(gProductQuality) < Integer.parseInt(gCQualityBill))
                        {

                            gNewUpdateQuality = Integer.parseInt(gCQuality) + (Integer.parseInt(gCQualityBill) - Integer.parseInt(gProductQuality));
                            gNowQuality = Integer.parseInt(gCQuality) + (Integer.parseInt(gCQualityBill) - Integer.parseInt(gProductQuality));

                        }else{

                            if(Integer.parseInt(gProductQuality) > Integer.parseInt(gCQualityBill)){

                                gNewUpdateQuality = Integer.parseInt(gCQuality) - (Integer.parseInt(gProductQuality) - Integer.parseInt(gCQualityBill));
                                gNowQuality = Integer.parseInt(gCQuality) - (Integer.parseInt(gProductQuality) - Integer.parseInt(gCQualityBill));

                            }

                        }



                        if (gIDTypeProduct == 0 || gIDProduct == 0 || gProductQuality.isEmpty()) {

                            Toast.makeText(UpdateBill.this, "Vui Lòng Nhập Đầy Đủ Thông Tin", Toast.LENGTH_SHORT).show();

                        } else {

                            if (gNowQuality < 0) {

                                progessLoading.show();
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(UpdateBill.this, "Số Lượng Hàng Trong Kho Không Đủ", Toast.LENGTH_SHORT).show();
                                        progessLoading.dismiss();
                                    }
                                }, 2000);

                            } else {

                                Boolean resultUpdateData = db.updateData_Bill(id_Bill, gProductQuality, gTotalPrice, gCreateDay, gCreateTime, gIDTypeProduct, gIDProduct);
                                if (resultUpdateData == true) {

                                    db.updateNewQualityProduct_Bill(gNewUpdateQuality.toString(), gIDProduct);
                                    progessLoading.show();
                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            Intent intent = new Intent(UpdateBill.this, Shopping.class);
                                            startActivity(intent);
                                            Toast.makeText(UpdateBill.this, "Sửa Hóa Đơn Thành Công", Toast.LENGTH_SHORT).show();
                                            progessLoading.dismiss();
                                        }
                                    }, 2000);

                                } else {

                                    progessLoading.show();
                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(UpdateBill.this, "Sửa Hóa Đơn Thất Bại", Toast.LENGTH_SHORT).show();
                                            progessLoading.dismiss();
                                        }
                                    }, 2000);
                                }
                            }
                        }
                    }
                }
            }
        });

        btnBack();
        loadDataTypeProduct();
        readAllData();
    }
    private void readAllData() {

        Cursor cursor = db.readAllData_Bill(id_Bill);
        if (cursor.getCount() == 0){
            Toast.makeText(this, "Không Có Hóa Đơn", Toast.LENGTH_SHORT).show();
        }else{
            while (cursor.moveToNext()){
                TypeProduct.setText(cursor.getString(1));
                NameProduct.setText(cursor.getString(2));
                PriceProduct.setText(cursor.getString(3));
                QualityProduct.setText(cursor.getString(4));
                TotalPrice.setText(cursor.getString(5));
                CreateDay.setText(cursor.getString(6));
                CreateTime.setText(cursor.getString(7));
                byte[] image = cursor.getBlob(8);
                Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
                ImageProduct.setImageBitmap(bitmap);
            }
        }

    }

    private void loadDataTypeProduct() {

        Cursor cursor = db.readTypeProduct_Bill(Integer.valueOf(idUser));

        itemTypeProduct = new ArrayList<>();
        if(cursor.getCount() == 0){

        }else{
            while (cursor.moveToNext()){
                itemTypeProduct.add(cursor.getString(1));
            }
            adapterItemTypeProduct = new ArrayAdapter<String>(this, R.layout.list_item_dropmenu,itemTypeProduct);
            TypeProduct.setAdapter(adapterItemTypeProduct);

        }

    }

    private void loadDataNameProduct() {

        Cursor cursor = db.readNameProduct_Bill(getIDTypeProduct);

        itemProduct = new ArrayList<>();
        if(cursor.getCount() == 0){

            Toast.makeText(this, "Loại Sản Phẩm này chưa Có Sản Phẩm", Toast.LENGTH_LONG).show();

        }else{
            while (cursor.moveToNext()){
                itemProduct.add(cursor.getString(0));
            }
            adapterItemProduct = new ArrayAdapter<String>(this, R.layout.list_item_dropmenu,itemProduct);
            NameProduct.setAdapter(adapterItemProduct);

        }

    }

    private void loadDataPriceProduct(String getNameProduct) {

        Cursor cursor = db.readPriceProduct_Bill(getNameProduct, Integer.valueOf(idUser));
        if(cursor.getCount() == 0){

        }else{
            while (cursor.moveToNext()){
                PriceProduct.setText(cursor.getString(0));
            }
        }
    }

    private void btnBack() {

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UpdateBill.super.onBackPressed();
            }
        });
    }
}