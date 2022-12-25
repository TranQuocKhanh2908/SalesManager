package com.example.projectandroid.User.MProduct.ImportProduct;

import static com.example.projectandroid.User.DashBoard.idUser;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
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

public class ImportProduct extends AppCompatActivity {

    ArrayList<String> itemTypeProduct, itemProduct;
    AutoCompleteTextView TypeProduct,NameProduct;
    ArrayAdapter adapterItemTypeProduct, adapterItemProduct;

    ImageView btnBack, ImageProduct;
    Button btnConfirm;
    TextInputEditText QualityProduct, PriceProduct, TotalPrice;
    Integer Total;

    SqlDatabaseHelper db;

    Button ConfirmBtnDia, CancelBtnDia;
    TextView ContentDia;
    Dialog dialog;

    int getIDTypeProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_import_product);
        db = new SqlDatabaseHelper(this);

        final ProgessLoading progessLoading = new ProgessLoading(this);

        btnBack = findViewById(R.id.back_btn);
        btnConfirm = findViewById(R.id.confirm_btn);
        TypeProduct = findViewById(R.id.type_product_importProduct);
        NameProduct = findViewById(R.id.name_product_importProduct);
        QualityProduct = findViewById(R.id.quality_product_importProduct);
        PriceProduct = findViewById(R.id.price_product_importProduct);
        TotalPrice = findViewById(R.id.total_price_importProduct);
        ImageProduct = findViewById(R.id.image_product_importProduct);

        TypeProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Cursor cursor = db.getIDTypeProduct_Bill(TypeProduct.getText().toString(), Integer.valueOf(idUser));

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
                    Toast.makeText(ImportProduct.this, "Vui Lòng Chọn Loại Sản Phẩm", Toast.LENGTH_SHORT).show();
                }
            }
        });

        NameProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                loadDataPriceProduct(NameProduct.getText().toString());
                loadDataImageProduct(NameProduct.getText().toString());
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

                    Toast.makeText(ImportProduct.this, "Vui Lòng Chọn Loại Sản Phẩm", Toast.LENGTH_SHORT).show();

                } else {

                    String gNameProduct = NameProduct.getText().toString();

                    Cursor cursorID_Product = db.getIDProduct_Bill(gNameProduct,Integer.valueOf(idUser));
                    if (cursorID_Product.getCount() == 0) {

                        Toast.makeText(ImportProduct.this, "Vui Lòng Chọn Sản Phẩm", Toast.LENGTH_SHORT).show();

                    } else {

                        Integer gIDTypeProduct = null;
                        Integer gIDProduct = null;
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

                            if (gIDTypeProduct == 0 || gIDProduct == 0 || gProductQuality.isEmpty()) {

                                Toast.makeText(ImportProduct.this, "Vui Lòng Nhập Đầy Đủ Thông Tin", Toast.LENGTH_SHORT).show();

                        } else {

                                String gOldQuality = null;
                                Cursor cursor = db.getQualityProduct_ImportProduct(gNameProduct, Integer.valueOf(idUser));
                                while (cursor.moveToNext()) {
                                    gOldQuality = cursor.getString(0);
                                }
                                Integer gNewProductQuality = Integer.parseInt(gOldQuality) + Integer.parseInt(gProductQuality);

                                Boolean resultInsertData = db.insertData_ImportProduct(gOldQuality, gNewProductQuality.toString(), gTotalPrice, gCreateDay, gCreateTime, gIDTypeProduct, gIDProduct, Integer.valueOf(idUser));
                                Boolean resultUpdateData = db.updateData_ImportProduct(gIDProduct, gNewProductQuality.toString());
                                if (resultInsertData == true && resultUpdateData == true) {

                                    progessLoading.show();
                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            Intent intent = new Intent(ImportProduct.this, CompleteImportProduct.class);
                                            startActivity(intent);
                                            progessLoading.dismiss();
                                        }
                                    }, 2000);

                                } else {

                                    progessLoading.show();
                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(ImportProduct.this, "Nhập Hàng Thất Bại", Toast.LENGTH_SHORT).show();
                                            progessLoading.dismiss();
                                        }
                                    }, 2000);

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

    private void loadDataTypeProduct() {

        Cursor cursor = db.readTypeProduct_ImportProduct(Integer.valueOf(idUser));

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

        Cursor cursor = db.readNameProduct_ImportProduct(getIDTypeProduct);

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

    private void loadDataPriceProduct(String getNameProduct) {

        Cursor cursor = db.readPriceProduct_ImportProduct(getNameProduct, Integer.valueOf(idUser));
        if(cursor.getCount() == 0){

        }else{
            while (cursor.moveToNext()){
                PriceProduct.setText(cursor.getString(0));
            }
        }
    }

    private void loadDataImageProduct(String getNameProduct) {

        Cursor cursor = db.readImageProduct_ImportProduct(getNameProduct, Integer.valueOf(idUser));
        if(cursor.getCount() == 0){

        }else{
            while (cursor.moveToNext()){
                byte[] image = cursor.getBlob(0);
                Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
                ImageProduct.setImageBitmap(bitmap);
            }
        }
    }

    public void ShowDiaLog() {

        dialog = new Dialog(ImportProduct.this);
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
                ImportProduct.super.onBackPressed();
            }
        });
    }
}