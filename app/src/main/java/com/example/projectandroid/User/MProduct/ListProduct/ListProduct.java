package com.example.projectandroid.User.MProduct.ListProduct;

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
import com.example.projectandroid.HelperClasses.Product.ListTypeProduct.ListTypeProductAdapter;
import com.example.projectandroid.HelperClasses.Product.ListTypeProduct.ListTypeProductHelperClass;
import com.example.projectandroid.HelperClasses.SqlLite.SqlDatabaseHelper;
import com.example.projectandroid.R;
import com.example.projectandroid.User.MProduct.AddProduct.AddProduct;

import java.util.ArrayList;

public class ListProduct extends AppCompatActivity {

    ImageView btnBack;
    SQLiteDatabase sqLiteDatabase;
    RecyclerView listProductRecycle;
    SqlDatabaseHelper db;
    SearchView searchView;
    ListProductAdapter listProductAdapter;
    ArrayList<ListProductHelperClass> listProductHelperClassArrayList = new ArrayList<>();

    Button ConfirmBtnDia, CancelBtnDia;
    TextView ContentDia;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_list_product);


        listProductRecycle = findViewById(R.id.List_Product_recycler);
        btnBack = findViewById(R.id.back_btn);
        searchView = findViewById(R.id.search_product);
        searchView.setQueryHint("Nhập Tên Sản Phẩm");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                listProductAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                listProductAdapter.getFilter().filter(newText);
                return false;
            }
        });

        db = new SqlDatabaseHelper(this);
        listProductRecycle.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL,false));

        btnBack();
        readData();
        ShowDiaLog();
    }

    private void readData() {

        Cursor cursor = db.readData_Product(Integer.valueOf(idUser));
        if(cursor.getCount() == 0){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    ContentDia.setText("Chưa Có Sản Phẩm, Bạn Có Muốn Thêm Không?");
                    dialog.show();
                }
            },500);
        }else{
            while (cursor.moveToNext()) {
                Integer idProduct = cursor.getInt(0);
                String productName = cursor.getString(2);
                String productQuality = cursor.getString(3);
                byte[] productImage = cursor.getBlob(6);
                listProductHelperClassArrayList.add(new ListProductHelperClass(productName, productQuality, productImage, idProduct));

            }

            listProductAdapter = new ListProductAdapter(this, R.layout.list_product_card_desgin, listProductHelperClassArrayList, sqLiteDatabase);
            listProductRecycle.setAdapter(listProductAdapter);

        }

    }

    public void ShowDiaLog() {

        dialog = new Dialog(ListProduct.this);
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
                Intent intent = new Intent(getApplicationContext(), AddProduct.class);
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
                ListProduct.super.onBackPressed();
            }
        });
    }
}