package com.example.projectandroid.User.Profile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.cardview.widget.CardView;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.projectandroid.HelperClasses.SqlLite.SqlDatabaseHelper;
import com.example.projectandroid.R;
import com.example.projectandroid.SessionManager;
import com.example.projectandroid.User.DashBoard;
import com.example.projectandroid.common.LoginSignUp.Login;
import com.example.projectandroid.common.LoginSignUp.StartUpScreen;

public class Profile extends AppCompatActivity {

    ImageView btnBack, imageUser;
    CardView Setting, changePassword, Information, InformationUser;
    Button editProfile, ConfirmBtnDia, CancelBtnDia;
    TextView ContentDia, nameUser,emailUser;
    
    RelativeLayout btnLogout;

    Dialog dialog;

    String idUser;

    SqlDatabaseHelper db;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_profile);

        db = new SqlDatabaseHelper(this);
        sessionManager = new SessionManager(this);

        btnBack = findViewById(R.id.back_btn);
        Setting = findViewById(R.id.card_icon_Setting_profile);
        changePassword = findViewById(R.id.card_icon_Password_profile);
        Information = findViewById(R.id.card_icon_Info_profile);
        InformationUser = findViewById(R.id.card_icon_info_user_profile);
        btnLogout = findViewById(R.id.logout_btn);
        editProfile = findViewById(R.id.btn_Edit_profile);
        nameUser = findViewById(R.id.text_name_Profile);
        emailUser = findViewById(R.id.text_email_Profile);
        imageUser = findViewById(R.id.Image_profile);


        idUser = sessionManager.getID();

        if(idUser.equals("0") || idUser.equals("")){

            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
            finish();

        }

        editProfile();
        btnBack();
        Setting();
        changePassword();
        Information();
        InformationUser();
        btnLogout();
        ShowDiaLog();
        readAllData();
    }

    private void InformationUser() {

        InformationUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), UserInformation.class);
                startActivity(intent);
            }
        });

    }

    private void editProfile() {

        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Profile.this, EditProfile.class);
                startActivity(intent);
            }
        });

    }

    private void readAllData() {

        Cursor cursor = db.readAllData_User(Integer.parseInt(idUser));
        while (cursor.moveToNext()){

            nameUser.setText(cursor.getString(3));
            emailUser.setText(cursor.getString(4));
            if (cursor.getBlob(8) == null){
                imageUser.setImageResource(R.drawable.account);
            }else{
                byte[] image = cursor.getBlob(8);
                Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
                imageUser.setImageBitmap(bitmap);
            }
        }

    }

    public void ShowDiaLog() {

        dialog = new Dialog(Profile.this);
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

    private void btnLogout() {

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentDia.setText("Bạn có chắc chắn muốn đăng xuất!?");
                dialog.show();
            }
        });


    }

    private void Information() {

        Information.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AppInformation.class);
                startActivity(intent);
            }
        });

    }

    private void changePassword() {

        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ChangePassword.class);
                startActivity(intent);
            }
        });

    }

    private void Setting() {

        Setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(getApplicationContext(), Setting.class);
                startActivity(intent);
            }
        });

    }

    private void btnBack() {

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), DashBoard.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_image_menu,menu);
        return true;
    }
}