package com.example.projectandroid.User;

import static com.example.projectandroid.User.DashBoard.idUser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.projectandroid.R;
import com.example.projectandroid.SessionManager;
import com.example.projectandroid.User.MShopping.CreateBill.CreateBill;
import com.example.projectandroid.User.MShopping.ListBill.ListBill;
import com.example.projectandroid.User.MShopping.ListPromotion.ListPromotion;
import com.example.projectandroid.User.MShopping.CreatePromotion.CreatePromotion;
import com.example.projectandroid.User.Profile.Profile;
import com.example.projectandroid.common.LoginSignUp.Login;
import com.example.projectandroid.common.LoginSignUp.StartUpScreen;
import com.google.android.material.navigation.NavigationView;

public class Shopping extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ImageView menuIcon;

    LinearLayout contentView;

    RelativeLayout btnCreateBill, btnCreatePromotion, btnListPromotion, btnListBill;

    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_shopping);

        sessionManager = new SessionManager(this);

        if(idUser.equals("0") || idUser.equals("")){

            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
            finish();

        }

        menuIcon = findViewById(R.id.menu_icon);
        contentView = findViewById(R.id.content);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);

        btnCreateBill = findViewById(R.id.createBill);
        btnCreatePromotion = findViewById(R.id.createPromotion);
        btnListPromotion = findViewById(R.id.listPromotion);
        btnListBill = findViewById(R.id.listBill);

        navigationDrawer();

        btnCreateBill();
        btnCreatePromotion();
        btnListPromotion();
        btnListBill();

    }

    private void btnListBill() {

        btnListBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =  new Intent(getApplicationContext(), ListBill.class);
                startActivity(intent );
            }
        });

    }

    private void btnListPromotion() {

        btnListPromotion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =  new Intent(getApplicationContext(), ListPromotion.class);
                startActivity(intent );
            }
        });

    }

    private void btnCreatePromotion() {

        btnCreatePromotion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =  new Intent(getApplicationContext(), CreatePromotion.class);
                startActivity(intent );
            }
        });

    }

    private void btnCreateBill() {

        btnCreateBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =  new Intent(getApplicationContext(), CreateBill.class);
                startActivity(intent );
            }
        });

    }

    private void navigationDrawer() {
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(Shopping.this);
        navigationView.setCheckedItem(R.id.nav_shopping);

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
                                               final float diffScaledOffset = slideOffset * (1 - DashBoard.END_SCALE);
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
                startActivity(new Intent(getApplicationContext(), StartUpScreen.class));
                sessionManager.setLogin(false);
                finish();

        }
        return true;
    }
}