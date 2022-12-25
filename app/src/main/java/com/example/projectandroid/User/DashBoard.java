package com.example.projectandroid.User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import com.example.projectandroid.ChannelNotification;
import com.example.projectandroid.HelperClasses.HomeAdapter.ListBillDashBoardAdapter;
import com.example.projectandroid.HelperClasses.HomeAdapter.ListBillDashBoardHelperClass;
import com.example.projectandroid.HelperClasses.HomeAdapter.ListProductDashBoardAdapter;
import com.example.projectandroid.HelperClasses.HomeAdapter.ListProductDashBoardHelperClass;
import com.example.projectandroid.HelperClasses.HomeAdapter.ListPromotionDashBoardAdapter;
import com.example.projectandroid.HelperClasses.HomeAdapter.ListPromotionDashBoardHelperClass;
import com.example.projectandroid.HelperClasses.Shopping.ListPromotion.ListPromotionAdapter;
import com.example.projectandroid.HelperClasses.Shopping.ListPromotion.ListPromotionHelperClass;
import com.example.projectandroid.HelperClasses.SqlLite.SqlDatabaseHelper;
import com.example.projectandroid.R;
import com.example.projectandroid.RecyclerViewMargin;
import com.example.projectandroid.SessionManager;
import com.example.projectandroid.User.MProduct.AddProduct.AddProduct;
import com.example.projectandroid.User.MProduct.AddTypeProduct.AddTypeProduct;
import com.example.projectandroid.User.MProduct.ImportProduct.ImportProduct;
import com.example.projectandroid.User.MShopping.CreateBill.CreateBill;
import com.example.projectandroid.User.MShopping.CreatePromotion.CreatePromotion;
import com.example.projectandroid.User.MShopping.ListBill.ListBill;
import com.example.projectandroid.User.MShopping.ListPromotion.ListPromotion;
import com.example.projectandroid.User.Profile.Profile;
import com.example.projectandroid.common.LoginSignUp.Login;
import com.example.projectandroid.common.LoginSignUp.StartUpScreen;
import com.google.android.material.navigation.NavigationView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DashBoard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static String idUser;

    static final float END_SCALE = 1f;

    RecyclerView productRecycle,billRecycle,promotionRecycle;

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ImageView menuIcon;
    Toolbar toolbar;
    TextView loginUserName, allBill, allPromotion;

    LinearLayout contentView;

    RelativeLayout btn_product, btn_shopping, btn_analysis;

    SqlDatabaseHelper db;

    SessionManager sessionManager;

    Button ConfirmBtnDia, CancelBtnDia;
    TextView ContentDia;
    Dialog dialog;

    SQLiteDatabase sqLiteDatabase;

    ListProductDashBoardAdapter listProductDashBoardAdapter;
    ArrayList<ListProductDashBoardHelperClass> listProductDashBoardHelperClassArrayList = new ArrayList<>();

    ListBillDashBoardAdapter listBillDashBoardAdapter;
    ArrayList<ListBillDashBoardHelperClass> listBillDashBoardHelperClassArrayList = new ArrayList<>();

    ListPromotionDashBoardAdapter listPromotionDashBoardAdapter;
    ArrayList<ListPromotionDashBoardHelperClass> listPromotionDashBoardHelperClassArrayList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_dash_board);

        db = new SqlDatabaseHelper(this);

        sessionManager = new SessionManager(this);

        productRecycle = findViewById(R.id.product_recycler_dashboard);
        billRecycle = findViewById(R.id.bill_recycler);
        promotionRecycle = findViewById(R.id.promotion_recycler);
        menuIcon = findViewById(R.id.menu_icon);
        loginUserName = findViewById(R.id.username_login);
        contentView = findViewById(R.id.content);
        btn_product = findViewById(R.id.dashboard_product);
        btn_shopping = findViewById(R.id.dashboard_shopping);
        btn_analysis = findViewById(R.id.dashboard_analysis);
        allBill = findViewById(R.id.all_bill);
        allPromotion = findViewById(R.id.all_promotion);
        toolbar = findViewById(R.id.dashboard_toolBar);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);

        idUser = sessionManager.getID();

        if(idUser.equals("0") || idUser.equals("")){

            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
            finish();

        }

        Cursor cursor = db.readDataForMenu_User(Integer.parseInt(idUser));
        while (cursor.moveToNext()){

            loginUserName.setText("Chào Mừng\n " + cursor.getString(0));

        }

        toolbar.inflateMenu(R.menu.option_menu_dashboard);

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){

                    case R.id.add_type_product:
                        Intent addTypeProduct = new Intent(getApplicationContext(), AddTypeProduct.class);
                        startActivity(addTypeProduct);
                        break;
                    case R.id.add_product:
                        Intent addProduct = new Intent(getApplicationContext(), AddProduct.class);
                        startActivity(addProduct);
                        break;
                    case R.id.import_product:
                        Intent importProduct = new Intent(getApplicationContext(), ImportProduct.class);
                        startActivity(importProduct);
                        break;
                    case R.id.create_bill:
                        Intent createBill = new Intent(getApplicationContext(), CreateBill.class);
                        startActivity(createBill);
                        break;
                    case R.id.create_promotion:
                        Intent createPromotion = new Intent(getApplicationContext(), CreatePromotion.class);
                        startActivity(createPromotion);
                        break;
                }
                return false;
            }
        });

        btn_product();
        btn_shopping();
        btn_analysis();
        ShowDiaLog();
        navigationDrawer();
        productRecycle();
        billRecycle();
        promotionRecycle();
        deletePromotion();
        AllBill();
        AllPromotion();
    }

    private void AllPromotion() {

        allPromotion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ListPromotion.class);
                startActivity(intent);
            }
        });

    }

    private void AllBill() {

        allBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ListBill.class);
                startActivity(intent);
            }
        });

    }

    private void sendNotification(Integer promotion_id) {

        Intent intent = new Intent(this, ListPromotion.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        String gName = null;
        String gPercent = null;
        String gSDate = null;
        String gEDate = null;
        byte[] image;
        Bitmap bitmap = null;
        Cursor cursor = db.readAllData_Promotion(promotion_id);
        if(cursor.getCount() == 0){
            Toast.makeText(this, "Không Có ID", Toast.LENGTH_SHORT).show();
        }else{
            while (cursor.moveToNext()){
                gName = cursor.getString(2);
                gPercent = cursor.getString(4) + "%";
                gSDate = cursor.getString(6);
                gEDate = cursor.getString(7);
                image = cursor.getBlob(8);
                bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);;
            }

            Notification notification = new NotificationCompat.Builder(this, ChannelNotification.CHANNEL_ID)
                    .setSmallIcon(R.drawable.promotion)
                    .setLargeIcon(bitmap)
                    .setContentTitle("Khuyến Mãi Sản Phẩm '"+ gName  +"' Đã Hết Hạn")
                    .setContentText("Giảm: "+ gPercent +" / Ngày Bắt Đầu: "+ gSDate +" / Ngày Kết Thúc: " +gEDate)
                    .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(bitmap).bigLargeIcon(null))
                    .setColor(getResources().getColor(R.color.mainColor))
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .build();

            NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
            notificationManagerCompat.notify(getNotificationId(), notification);
        }
    }

    private int getNotificationId(){
        return  (int) new Date().getTime();
    }

    private void deletePromotion() {

        Date dateAndTime = Calendar.getInstance().getTime();
        Cursor cursor = db.readEndDay_Promotion(Integer.valueOf(idUser));
        if (cursor.getCount() == 0){

        }else {
            while (cursor.moveToNext()) {
                Integer gID = cursor.getInt(0);
                String gEDate = cursor.getString(1);
                SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());
                try {
                    Date gDate = dateFormat.parse(gEDate);
                    if (dateAndTime.after(gDate)) {
                        sendNotification(gID);
                        db.deleteData_Promotion(gID);
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void btn_product() {
        btn_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DashBoard.this, Product.class));
            }
            });
        }

    private void btn_shopping() {
        btn_shopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashBoard.this, Shopping.class);
                startActivity(intent);
            }
        });
    }

    private void btn_analysis() {
        btn_analysis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashBoard.this, Analysis.class);
            startActivity(intent);
        }
    });
}

    private void navigationDrawer() {
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(DashBoard.this);
        navigationView.setCheckedItem(R.id.nav_home);

        menuIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(drawerLayout.isDrawerVisible(GravityCompat.START)){
                    drawerLayout.closeDrawer(GravityCompat.START);
                }else{
                    drawerLayout.openDrawer(GravityCompat.START);
                }
            }
        });

        animateNavigationDrawer();

    }

    private void animateNavigationDrawer() {

        drawerLayout.setScrimColor(Color.parseColor("#FF9800"));
        drawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
                                           @Override
                                           public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {
                                               final float diffScaledOffset = slideOffset * (1 - END_SCALE);
                                               final float offsetScale = 1 - diffScaledOffset;
                                               contentView.setScaleX(offsetScale);
                                               contentView.setScaleY(offsetScale);


                                               final float xOffset = drawerView.getWidth() * slideOffset;
                                               final float xOffsetDiff = contentView.getWidth() * diffScaledOffset / 2;
                                               final float xTranslation = xOffset - xOffsetDiff;
                                               contentView.setTranslationX(xTranslation);
                                           }
                                       }
        );

    }

    @Override
    public void onBackPressed(){

        if(drawerLayout.isDrawerVisible(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){

            case R.id.nav_home:
                startActivity(new Intent(getApplicationContext(), DashBoard.class));
                break;

            case R.id.nav_product:
                startActivity(new Intent(getApplicationContext(), Product.class));
                break;

            case R.id.nav_shopping:
                startActivity(new Intent(getApplicationContext(), Shopping.class));
                break;

            case R.id.nav_analysis:
                startActivity(new Intent(getApplicationContext(), Analysis.class));
                break;
            case R.id.nav_profile:
                startActivity(new Intent(getApplicationContext(), Profile.class));
                break;
            case R.id.nav_logout:
                ContentDia.setText("Bạn Có Chắc Chắn Muốn Đăng Xuất!?");
                dialog.show();

        }
        return true;
    }

    public void ShowDiaLog() {

        dialog = new Dialog(DashBoard.this);
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
                Intent intent = new Intent(getApplicationContext(), StartUpScreen.class);
                startActivity(intent);
                finish();
                sessionManager.setLogin(false);
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

    private void productRecycle() {

        RecyclerViewMargin decoration = new RecyclerViewMargin(20);
        productRecycle.addItemDecoration(decoration);
        productRecycle.setHasFixedSize(false);
        productRecycle.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));


        Cursor cursor = db.readData_Product(Integer.valueOf(idUser));
        if(cursor.getCount() == 0){

        }else {
            while (cursor.moveToNext()) {
                Integer idProduct = cursor.getInt(0);
                String productName = cursor.getString(2);
                String productQuality = cursor.getString(3);
                byte[] productImage = cursor.getBlob(6);
                listProductDashBoardHelperClassArrayList.add(new ListProductDashBoardHelperClass(productName, productQuality, productImage, idProduct));

            }

            listProductDashBoardAdapter = new ListProductDashBoardAdapter(this, R.layout.list_product_dashboard_card_design, listProductDashBoardHelperClassArrayList, sqLiteDatabase);
            productRecycle.setAdapter(listProductDashBoardAdapter);
        }
    }

    private void billRecycle() {

        RecyclerViewMargin decoration = new RecyclerViewMargin(20);
        billRecycle.addItemDecoration(decoration);
        billRecycle.setHasFixedSize(false);
        billRecycle.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        Cursor cursor = db.readData_Bill(Integer.valueOf(idUser));
        if(cursor.getCount() == 0){

        }else {
            while (cursor.moveToNext()) {
                Integer IdBill = cursor.getInt(0);
                String ProductName = cursor.getString(1);
                String BillCreateDay = cursor.getString(3);
                byte[] ProductImage = cursor.getBlob(4);
                String BillCreateTime = cursor.getString(5);
                listBillDashBoardHelperClassArrayList.add(new ListBillDashBoardHelperClass(ProductName, BillCreateDay, BillCreateTime, IdBill, ProductImage));

            }
            listBillDashBoardAdapter = new ListBillDashBoardAdapter(this, R.layout.list_bill_card_desgin, listBillDashBoardHelperClassArrayList, sqLiteDatabase);
            billRecycle.setAdapter(listBillDashBoardAdapter);
        }
    }

    private void promotionRecycle() {

        RecyclerViewMargin decoration = new RecyclerViewMargin(20);
        promotionRecycle.addItemDecoration(decoration);
        promotionRecycle.setHasFixedSize(false);
        promotionRecycle.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        Cursor cursor = db.readData_Promotion(Integer.valueOf(idUser));
        if(cursor.getCount() == 0){

        }else {
            while (cursor.moveToNext()) {
                Integer IdPromotion = cursor.getInt(0);
                String productName = cursor.getString(1);
                String promotionStartDay = cursor.getString(3);
                String promotionEndDay = cursor.getString(4);
                byte[] productImage = cursor.getBlob(5);
                listPromotionDashBoardHelperClassArrayList.add(new ListPromotionDashBoardHelperClass(productName, promotionStartDay, promotionEndDay, productImage, IdPromotion));

            }
            listPromotionDashBoardAdapter = new ListPromotionDashBoardAdapter(this, R.layout.list_promotion_dashboard_card_design, listPromotionDashBoardHelperClassArrayList, sqLiteDatabase);
            promotionRecycle.setAdapter(listPromotionDashBoardAdapter);
        }
    }
}