package com.example.projectandroid.User.MShopping.ListBill;

import static com.example.projectandroid.User.DashBoard.idUser;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.projectandroid.HelperClasses.Product.ListProduct.ListProductAdapter;
import com.example.projectandroid.HelperClasses.Product.ListProduct.ListProductHelperClass;
import com.example.projectandroid.HelperClasses.Shopping.ListBill.ListBillAdapter;
import com.example.projectandroid.HelperClasses.Shopping.ListBill.ListBillHelperClass;
import com.example.projectandroid.HelperClasses.SqlLite.SqlDatabaseHelper;
import com.example.projectandroid.R;
import com.example.projectandroid.User.MProduct.AddProduct.AddProduct;
import com.example.projectandroid.User.MShopping.CreateBill.CreateBill;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ListBill extends AppCompatActivity {

    ImageView btnBack;
    SQLiteDatabase sqLiteDatabase;
    RecyclerView listBillRecycle;
    SqlDatabaseHelper db;

    SearchView searchView;
    ListBillAdapter listBillAdapter;
    ArrayList<ListBillHelperClass> listBillHelperClassArrayList = new ArrayList<>();

    Button ConfirmBtnDia, CancelBtnDia;
    TextView ContentDia;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_list_bill);

        listBillRecycle = findViewById(R.id.List_Bill_recycler);
        btnBack = findViewById(R.id.back_btn);
        searchView = findViewById(R.id.search_bill);
        searchView.setQueryHint("Nhập Tên Sản Phẩm");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                listBillAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                listBillAdapter.getFilter().filter(newText);
                return false;
            }
        });

        db = new SqlDatabaseHelper(this);
        listBillRecycle.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));


        btnBack();
        readData();
        ShowDiaLog();
    }

    private void readData() {

        Cursor cursor = db.readData_Bill(Integer.valueOf(idUser));
        if(cursor.getCount() == 0){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    ContentDia.setText("Chưa Có Hóa Đơn, Bạn Có Muốn Tạo Không?");
                    dialog.show();
                }
            },500);
        }else{
            while (cursor.moveToNext()){
                Integer IdBill = cursor.getInt(0);
                String ProductName = cursor.getString(1);
                String ProductQuality = cursor.getString(2);
                String BillCreateDay = cursor.getString(3);
                byte[] ProductImage = cursor.getBlob(4);
                listBillHelperClassArrayList.add(new ListBillHelperClass(ProductName,ProductQuality,BillCreateDay,ProductImage,IdBill));

            }
            listBillAdapter = new ListBillAdapter(this, R.layout.list_bill_card_desgin, listBillHelperClassArrayList, sqLiteDatabase);
            listBillRecycle.setAdapter(listBillAdapter);
        }

    }

    public void ShowDiaLog() {

        dialog = new Dialog(ListBill.this);
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
                Intent intent = new Intent(getApplicationContext(), CreateBill.class);
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
                ListBill.super.onBackPressed();
            }
        });

    }


}