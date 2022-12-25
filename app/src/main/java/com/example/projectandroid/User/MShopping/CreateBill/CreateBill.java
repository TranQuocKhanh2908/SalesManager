package com.example.projectandroid.User.MShopping.CreateBill;

import static com.example.projectandroid.User.DashBoard.idUser;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.StrikethroughSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectandroid.HelperClasses.SqlLite.SqlDatabaseHelper;
import com.example.projectandroid.ProgessLoading;
import com.example.projectandroid.R;
import com.example.projectandroid.User.MProduct.AddTypeProduct.AddTypeProduct;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CreateBill extends AppCompatActivity {

    ArrayList<String> itemTypeProduct, itemProduct;
    AutoCompleteTextView TypeProduct,NameProduct;
    ArrayAdapter adapterItemTypeProduct, adapterItemProduct;

    ImageView btnBack, ImageProduct;
    Button btnConfirm;
    TextInputEditText QualityProduct, PriceProduct, TotalPrice;
    Float Total;

    SqlDatabaseHelper db;

    Button ConfirmBtnDia, CancelBtnDia;
    TextView ContentDia;
    Dialog dialog;

    int getIDTypeProduct;
    Integer gIDProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_create_bill);

        db = new SqlDatabaseHelper(this);

        final ProgessLoading progessLoading = new ProgessLoading(this);

        btnBack = findViewById(R.id.back_btn);
        btnConfirm = findViewById(R.id.confirm_btn);
        TypeProduct = findViewById(R.id.type_product_createBill);
        NameProduct = findViewById(R.id.name_product_createBill);
        QualityProduct = findViewById(R.id.quality_product_createBill);
        PriceProduct = findViewById(R.id.price_product_createBill);
        TotalPrice = findViewById(R.id.total_price_createBill);
        ImageProduct = findViewById(R.id.image_product_createBill);

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
                    Toast.makeText(CreateBill.this, "Vui Lòng Chọn Loại Sản Phẩm", Toast.LENGTH_SHORT).show();
                }
            }
        });

        NameProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String gNameProduct = NameProduct.getText().toString();
                Cursor cursorID_Product = db.getIDProduct_Bill(gNameProduct,Integer.valueOf(idUser));
                while (cursorID_Product.moveToNext()) {
                    gIDProduct = cursorID_Product.getInt(0);
                Date dateAndTime = Calendar.getInstance().getTime();
                SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());
                String gCDay = dateFormat.format(dateAndTime);
                Boolean resultCheckPromotion = db.checkProductPromotion_Bill(gIDProduct,gCDay);
                if (resultCheckPromotion == true) {
                    Toast.makeText(CreateBill.this, "Sản Phẩm Này Đang Được Giảm Giá, Giá Được Giảm Sẽ Được Hiển Thị", Toast.LENGTH_LONG).show();
                    loadDataImageProduct(NameProduct.getText().toString());
                    getPricePromotion(gIDProduct,gNameProduct);
                }else {
                    loadDataPriceProduct(NameProduct.getText().toString());
                    loadDataImageProduct(NameProduct.getText().toString());
                    PriceProduct.setText(cursorID_Product.getString(1));
                }
                }
            }
        });

        QualityProduct.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (TextUtils.isEmpty(QualityProduct.getText().toString())) {
                    Total = Float.valueOf(0);
                } else {
                    String pricePromotion = null;
                    String gNameProduct = NameProduct.getText().toString();
                    Cursor cursorID_Product = db.getIDProduct_Bill(gNameProduct, Integer.valueOf(idUser));
                    while (cursorID_Product.moveToNext()) {
                        gIDProduct = cursorID_Product.getInt(0);
                    }
                    Date dateAndTime = Calendar.getInstance().getTime();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());
                    String gCDay = dateFormat.format(dateAndTime);
                    Boolean resultCheckPromotion = db.checkProductPromotion_Bill(gIDProduct,gCDay);
                    if (resultCheckPromotion == true) {
                        Cursor cursorGetPricePromotion = db.getProductPromotion_Bill(gIDProduct);
                        if(cursorGetPricePromotion.getCount() == 0){

                        }else {
                            while (cursorGetPricePromotion.moveToNext()) {
                                pricePromotion = cursorGetPricePromotion.getString(0);
                            }
                        }
                        Total = Float.parseFloat(pricePromotion) * Integer.parseInt(QualityProduct.getText().toString());
                    }else{
                        Total = Float.valueOf(Integer.parseInt(PriceProduct.getText().toString()) * Integer.parseInt(QualityProduct.getText().toString()));
                    }
                    TotalPrice.setText(Total.toString());
                }
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

                    Toast.makeText(CreateBill.this, "Vui Lòng Chọn Loại Sản Phẩm", Toast.LENGTH_SHORT).show();

                } else {

                    String gNameProduct = NameProduct.getText().toString();

                    Cursor cursorID_Product = db.getIDProduct_Bill(gNameProduct,Integer.valueOf(idUser));
                    if (cursorID_Product.getCount() == 0) {

                        Toast.makeText(CreateBill.this, "Vui Lòng Chọn Sản Phẩm", Toast.LENGTH_SHORT).show();

                    } else {

                        Integer gIDTypeProduct = null;
                        gIDProduct = null;
                        String gCQuality = null;
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

                        Cursor cursorGetCQuality = db.getQualityProduct_Bill(gIDProduct);
                        while (cursorGetCQuality.moveToNext()) {
                            gCQuality = cursorGetCQuality.getString(0);
                        }

                        Integer gNowQuality = Integer.parseInt(gCQuality) - Integer.parseInt(gProductQuality);

                        if (gIDTypeProduct == 0 || gIDProduct == 0 || gProductQuality.isEmpty()) {

                            Toast.makeText(CreateBill.this, "Vui Lòng Nhập Đầy Đủ Thông Tin", Toast.LENGTH_SHORT).show();

                        } else {

                            if (gNowQuality < 0) {

                                progessLoading.show();
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(CreateBill.this, "Số Lượng Hàng Trong Kho Không Đủ", Toast.LENGTH_SHORT).show();
                                        progessLoading.dismiss();
                                    }
                                }, 2000);

                            } else {

                                Boolean resultInsertData = db.insertData_Bill(gProductQuality, gTotalPrice, gCreateDay, gCreateTime, gIDTypeProduct, gIDProduct, Integer.valueOf(idUser));
                                if (resultInsertData == true) {

                                    db.updateNewQualityProduct_Bill(gNowQuality.toString(), gIDProduct);
                                    progessLoading.show();
                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            Intent intent = new Intent(CreateBill.this, CompleteCreateBill.class);
                                            startActivity(intent);
                                            progessLoading.dismiss();
                                        }
                                    }, 2000);

                                } else {

                                    progessLoading.show();
                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(CreateBill.this, "Tạo Hóa Đơn Thất Bại", Toast.LENGTH_SHORT).show();
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
        ShowDiaLog();
    }

    private void getPricePromotion(Integer id_product, String product_name){


        String price = null;
        String pricePromotion = null;
        Cursor cursor = db.readPriceProduct_Bill(product_name, Integer.valueOf(idUser));
        if(cursor.getCount() == 0){

        }else{
            while (cursor.moveToNext()){
                price = cursor.getString(0);
            }
        }
        Cursor cursorGetPricePromotion = db.getProductPromotion_Bill(id_product);
        if(cursorGetPricePromotion.getCount() == 0){

        }else {
            while (cursorGetPricePromotion.moveToNext()) {
                pricePromotion = cursorGetPricePromotion.getString(0);
            }
        }

        Integer lText = price.length();
        String str = price + " -> " + pricePromotion;
        SpannableString ss = new SpannableString(str);
        StrikethroughSpan strikethroughSpan = new StrikethroughSpan();
        ss.setSpan(strikethroughSpan,0, lText, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        PriceProduct.setText(ss);
    }

    private void loadDataTypeProduct() {

        Cursor cursor = db.readTypeProduct_Bill(Integer.valueOf(idUser));

        itemTypeProduct = new ArrayList<>();
        if(cursor.getCount() == 0){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    ContentDia.setText("Chưa có Loại Sản Phẩm, Bạn Có Muốn Thêm Không?");
                    dialog.show();
                }
            },500);
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
            String[] adapter = {""};
            adapterItemProduct = new ArrayAdapter<String>(this, R.layout.list_item_dropmenu, adapter);
            NameProduct.setAdapter(adapterItemProduct);

        }else{
            while (cursor.moveToNext()){
                itemProduct.add(cursor.getString(0));
            }
            adapterItemProduct = new ArrayAdapter<String>(this, R.layout.list_item_dropmenu,itemProduct);
            NameProduct.setAdapter(adapterItemProduct);

        }

    }

    private void loadDataImageProduct(String getNameProduct) {

        Cursor cursor = db.readImageProduct_Bill(getNameProduct, Integer.valueOf(idUser));
        if(cursor.getCount() == 0){

        }else{
            while (cursor.moveToNext()){
                byte[] image = cursor.getBlob(0);
                Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
                ImageProduct.setImageBitmap(bitmap);
            }
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

    public void ShowDiaLog() {

        dialog = new Dialog(CreateBill.this);
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
                Intent intent = new Intent(getApplicationContext(), AddTypeProduct.class);
                startActivity(intent);
                dialog.dismiss();
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
                CreateBill.super.onBackPressed();
            }
        });
    }
}