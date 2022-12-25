package com.example.projectandroid.User.MProduct.ListTypeProduct;

import androidx.annotation.NonNull;
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
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectandroid.HelperClasses.Product.ListTypeProduct.ListTypeProductAdapter;
import com.example.projectandroid.HelperClasses.Product.ListTypeProduct.ListTypeProductHelperClass;
import com.example.projectandroid.HelperClasses.SqlLite.SqlDatabaseHelper;
import com.example.projectandroid.ProgessLoading;
import com.example.projectandroid.R;
import com.example.projectandroid.SessionManager;
import com.example.projectandroid.User.MProduct.AddTypeProduct.AddTypeProduct;
import com.example.projectandroid.User.Product;

import java.util.ArrayList;

public class ListTypeProduct extends AppCompatActivity {

    ImageView btnBack;
    SQLiteDatabase sqLiteDatabase;
    RecyclerView listProductRecycle;
    SqlDatabaseHelper db;
    SearchView searchView;
    ListTypeProductAdapter listTypeProductAdapter;
    ArrayList<ListTypeProductHelperClass> listTypeProductHelperClassArrayList = new ArrayList<>();

    Button ConfirmBtnDia, CancelBtnDia;
    TextView ContentDia;
    Dialog dialog;

    ProgessLoading progessLoading;

    String idUser;

    Integer id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_list_type_product);

        listTypeProductAdapter = new ListTypeProductAdapter(this, R.layout.list_type_product_card_desgin, listTypeProductHelperClassArrayList, sqLiteDatabase);

        progessLoading = new ProgessLoading(this);

        SessionManager sessionManager = new SessionManager(this);
        idUser = sessionManager.getID();

        listProductRecycle = findViewById(R.id.List_Type_Product_recycler);
        btnBack = findViewById(R.id.back_btn);
        searchView = findViewById(R.id.search_typeProduct);
        searchView.setQueryHint("Nhập Tên Loại Sản Phẩm");

        db = new SqlDatabaseHelper(this);
        listProductRecycle.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL,false));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                listTypeProductAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                listTypeProductAdapter.getFilter().filter(newText);
                return false;
            }
        });

        btnBack();
        readData();
        ShowDiaLog();

    }

    private void readData() {

        Cursor cursor = db.readData_TypeProduct(Integer.valueOf(idUser));
        if(cursor.getCount() == 0){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    ContentDia.setText("Chưa Có Loại Sản Phẩm, Bạn Có Muốn Thêm Không?");
                    dialog.show();
                }
            },500);
        }else{
            while (cursor.moveToNext()) {
                id = cursor.getInt(0);
                String typeProductName = cursor.getString(1);
                String typeProductDesc = cursor.getString(2);
                listTypeProductHelperClassArrayList.add(new ListTypeProductHelperClass(typeProductName, typeProductDesc, id));

            }
            listProductRecycle.setAdapter(listTypeProductAdapter);

        }

    }

    public void ShowDiaLog() {

        dialog = new Dialog(ListTypeProduct.this);
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
                ListTypeProduct.super.onBackPressed();
            }
        });

    }

    @Override
    public void onBackPressed() {
        if(!searchView.isIconified()){
            searchView.setIconified(true);
            return;
        }
        super.onBackPressed();
    }
}