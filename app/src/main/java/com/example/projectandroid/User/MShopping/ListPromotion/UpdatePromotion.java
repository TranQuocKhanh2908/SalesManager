package com.example.projectandroid.User.MShopping.ListPromotion;

import static com.example.projectandroid.User.DashBoard.idUser;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectandroid.HelperClasses.SqlLite.SqlDatabaseHelper;
import com.example.projectandroid.MinMaxValueFilter;
import com.example.projectandroid.ProgessLoading;
import com.example.projectandroid.R;
import com.example.projectandroid.User.MProduct.AddTypeProduct.AddTypeProduct;
import com.example.projectandroid.User.Shopping;
import com.google.android.material.textfield.TextInputEditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class UpdatePromotion extends AppCompatActivity {

    ArrayList<String> itemTypeProduct, itemProduct;
    AutoCompleteTextView TypeProduct, NameProduct;
    ArrayAdapter adapterItemTypeProduct, adapterItemProduct;
    TextInputEditText PercentPromotion, PriceProduct, PriceAfterPromotion, StartDay, EndDay;;
    Double priceAfterPercent;
    Button btnConfirm;
    ImageView btnBack, ImageProduct;

    SqlDatabaseHelper db;

    Button ConfirmBtnDia, CancelBtnDia;
    TextView ContentDia;
    Dialog dialog;

    int getIDTypeProduct;

    int Id_Promotion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_promotion);

        db = new SqlDatabaseHelper(this);

        final ProgessLoading progessLoading = new ProgessLoading(this);

        btnConfirm = findViewById(R.id.confirm_btn);
        btnBack = findViewById(R.id.back_btn);
        TypeProduct = findViewById(R.id.type_product_updatePromotion);
        NameProduct = findViewById(R.id.name_product_updatePromotion);
        PercentPromotion = findViewById(R.id.percent_updatePromotion);
        PriceProduct = findViewById(R.id.price_product_updatePromotion);
        PriceAfterPromotion = findViewById(R.id.priceAfter_updatePromotion);
        StartDay = findViewById(R.id.startDay_updatePromotion);
        EndDay = findViewById(R.id.endDay_updatePromotion);
        ImageProduct = findViewById(R.id.image_product_updatePromotion);

        PercentPromotion.setFilters( new InputFilter[] { new MinMaxValueFilter("0", "100")});

        Intent i = getIntent();
        Integer id = i.getIntExtra("Id_Promotion",0);
        Id_Promotion = id;

        TypeProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Cursor cursor = db.getIDTypeProduct_Bill(TypeProduct.getText().toString(),Integer.valueOf(idUser));

                if (cursor.getCount() == 0) {

                } else {
                    while (cursor.moveToNext()) {
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
                if (TextUtils.isEmpty(TypeProduct.getText().toString())) {
                    Toast.makeText(UpdatePromotion.this, "Vui Lòng Chọn Loại Sản Phẩm", Toast.LENGTH_SHORT).show();
                }
            }
        });

        NameProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                loadDataPriceProduct(NameProduct.getText().toString());
            }
        });

        PercentPromotion.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (TextUtils.isEmpty(PercentPromotion.getText().toString())) {
                    priceAfterPercent = Double.valueOf(0);
                } else {
                    priceAfterPercent = Double.parseDouble(PriceProduct.getText().toString()) * Integer.parseInt(PercentPromotion.getText().toString()) / 100;
                    priceAfterPercent = Double.parseDouble(PriceProduct.getText().toString()) - priceAfterPercent;
                }
                PriceAfterPromotion.setText(priceAfterPercent.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SimpleDateFormat dateFormat = new SimpleDateFormat("MM/d/yyyy");

                Calendar c = Calendar.getInstance();

                String gCDate = dateFormat.format(c.getTime());
                String gSDate = StartDay.getText().toString();
                String gEDate = EndDay.getText().toString();

                try {
                    Date gCurrentDay = dateFormat.parse(gCDate);
                    Date gStartDay = dateFormat.parse(gSDate);
                    Date gEndDay = dateFormat.parse(gEDate);

                    String gTypeProduct = TypeProduct.getText().toString();

                    Cursor cursorID_TypeProduct = db.getIDTypeProduct_Promotion(gTypeProduct, Integer.valueOf(idUser));
                    if (cursorID_TypeProduct.getCount() == 0) {

                        Toast.makeText(UpdatePromotion.this, "Vui Lòng Chọn Loại Sản Phẩm", Toast.LENGTH_SHORT).show();

                    } else {

                        String gNameProduct = NameProduct.getText().toString();

                        Cursor cursorID_Product = db.getIDProduct_Promotion(gNameProduct, Integer.valueOf(idUser));
                        if (cursorID_Product.getCount() == 0) {

                            Toast.makeText(UpdatePromotion.this, "Vui Lòng Chọn Sản Phẩm", Toast.LENGTH_SHORT).show();

                        }

                        if (gStartDay.before(gCurrentDay)) {

                            Toast.makeText(UpdatePromotion.this, "Vui Lòng Nhập Ngày Bắt Đầu Sau Hoặc Bằng Ngày Hiện Tại", Toast.LENGTH_LONG).show();

                        } else {

                            if (gStartDay.after(gEndDay)) {

                                Toast.makeText(UpdatePromotion.this, "Vui Lòng Nhập Ngày Kết Thúc Sau Ngày Bắt Đầu", Toast.LENGTH_LONG).show();

                            } else {

                                Integer gIDTypeProduct = null;
                                Integer gIDProduct = null;
                                String gPercent = PercentPromotion.getText().toString();
                                String gPriceAfterPromotion = PriceAfterPromotion.getText().toString();

                                while (cursorID_TypeProduct.moveToNext()) {
                                    gIDTypeProduct = cursorID_TypeProduct.getInt(0);
                                }
                                while (cursorID_Product.moveToNext()) {
                                    gIDProduct = cursorID_Product.getInt(0);
                                }

                                if (gPercent.isEmpty() || gPriceAfterPromotion.isEmpty()) {

                                    Toast.makeText(UpdatePromotion.this, "Vui Lòng Nhập Đầy Đủ Thông Tin", Toast.LENGTH_SHORT).show();

                                } else {

                                    Boolean resultInsertData = db.updateData_Promotion(Id_Promotion, gPercent, gPriceAfterPromotion, gSDate, gEDate, gIDTypeProduct, gIDProduct);
                                    if (resultInsertData == true) {

                                        progessLoading.show();
                                        new Handler().postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                Intent intent = new Intent(UpdatePromotion.this, Shopping.class);
                                                startActivity(intent);
                                                Toast.makeText(getApplicationContext(), "Sửa Khuyến Mãi Thành Công", Toast.LENGTH_SHORT).show();
                                                progessLoading.dismiss();
                                            }
                                        }, 2000);

                                    } else {

                                        progessLoading.show();
                                        new Handler().postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                Toast.makeText(UpdatePromotion.this, "Sửa Khuyến Mãi Thất Bại", Toast.LENGTH_SHORT).show();
                                                progessLoading.dismiss();
                                            }
                                        }, 2000);
                                    }
                                }
                            }
                        }
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });


        btnBack();
        ShowDiaLog();
        loadDataTypeProduct();
        readAllData();
        startDay();
        endDay();
    }

    private void endDay() {

        EndDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PickDateEDay();
            }
        });

    }

    private void startDay() {

        StartDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PickDateSDay();
            }
        });

    }

    private void PickDateSDay(){
        String SDay = StartDay.getText().toString();
        String splitText[] = SDay.split("/");
        int day = Integer.parseInt(splitText[1]);
        int month = Integer.parseInt(splitText[0]) - 1;
        int year = Integer.parseInt(splitText[2]);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                i1 = i1 + 1;
                String date = i1 + "/" + i2 + "/" + i;
                StartDay.setText(date);
            }
        },year,month,day);
        datePickerDialog.show();

    }

    private void PickDateEDay(){
        String SDay = EndDay.getText().toString();
        String splitText[] = SDay.split("/");
        int day = Integer.parseInt(splitText[1]);
        int month = Integer.parseInt(splitText[0]) - 1;
        int year = Integer.parseInt(splitText[2]);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                i1 = i1 + 1;
                String date = i1 + "/" + i2 + "/" + i;
                EndDay.setText(date);
            }
        },year,month,day);
        datePickerDialog.show();

    }

    private void readAllData() {

        Cursor cursor = db.readAllData_Promotion(Id_Promotion);
        if(cursor.getCount() == 0){
            Toast.makeText(this, "Không Có Khuyến Mãi", Toast.LENGTH_SHORT).show();
        }else{
            while (cursor.moveToNext()){
                TypeProduct.setText(cursor.getString(1));
                NameProduct.setText(cursor.getString(2));
                PriceProduct.setText(cursor.getString(3));
                PercentPromotion.setText(cursor.getString(4));
                PriceAfterPromotion.setText(cursor.getString(5));
                StartDay.setText(cursor.getString(6));
                EndDay.setText(cursor.getString(7));
                byte[] image = cursor.getBlob(8);
                Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
                ImageProduct.setImageBitmap(bitmap);
            }
        }
    }

    private void loadDataTypeProduct() {

        Cursor cursor = db.readTypeProduct_Promotion(Integer.valueOf(idUser));

        itemTypeProduct = new ArrayList<>();
        if (cursor.getCount() == 0) {

        } else {
            while (cursor.moveToNext()) {
                itemTypeProduct.add(cursor.getString(1));
            }
            adapterItemTypeProduct = new ArrayAdapter<String>(this, R.layout.list_item_dropmenu, itemTypeProduct);
            TypeProduct.setAdapter(adapterItemTypeProduct);

        }

    }

    private void loadDataNameProduct() {

        Cursor cursor = db.readNameProduct_Promotion(getIDTypeProduct);

        itemProduct = new ArrayList<>();
        if (cursor.getCount() == 0) {

            Toast.makeText(this, "Loại Sản Phẩm này chưa Có Sản Phẩm", Toast.LENGTH_LONG).show();

        } else {
            while (cursor.moveToNext()) {
                itemProduct.add(cursor.getString(0));
            }
            adapterItemProduct = new ArrayAdapter<String>(this, R.layout.list_item_dropmenu, itemProduct);
            NameProduct.setAdapter(adapterItemProduct);

        }

    }

    private void loadDataPriceProduct(String getNameProduct) {

        Cursor cursor = db.readPriceProduct_Promotion(getNameProduct, Integer.valueOf(idUser));
        if (cursor.getCount() == 0) {

        } else {
            while (cursor.moveToNext()) {
                PriceProduct.setText(cursor.getString(0));
            }
        }
    }

    public void ShowDiaLog() {

        dialog = new Dialog(UpdatePromotion.this);
        dialog.setContentView(R.layout.custom_dialog);
        dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.bg_dialog));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
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
                UpdatePromotion.super.onBackPressed();
            }
        });
    }
}