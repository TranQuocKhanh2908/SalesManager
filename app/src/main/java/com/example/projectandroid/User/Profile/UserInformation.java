package com.example.projectandroid.User.Profile;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.projectandroid.HelperClasses.SqlLite.SqlDatabaseHelper;
import com.example.projectandroid.ProgessLoading;
import com.example.projectandroid.R;
import com.example.projectandroid.SessionManager;
import com.google.android.material.textfield.TextInputEditText;

public class UserInformation extends AppCompatActivity {

    ImageView btnBack, eImageUser;
    TextInputEditText eName, eUserName, eEmail, ePhone, eDate, eGender;
    Button changeBtn;
    TextView createDayUser;

    SqlDatabaseHelper db;
    SessionManager sessionManager;

    String idUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_infomation);

        db = new SqlDatabaseHelper(this);

        sessionManager = new SessionManager(this);

        btnBack = findViewById(R.id.back_btn);
        changeBtn = findViewById(R.id.btn_Edit_infoAccount);
        eName = findViewById(R.id.name_infoAccount);
        eUserName = findViewById(R.id.user_name_infoAccount);
        eEmail = findViewById(R.id.email_infoAccount);
        ePhone = findViewById(R.id.phone_infoAccount);
        eDate = findViewById(R.id.age_infoAccount);
        eGender = findViewById(R.id.gender_infoAccount);
        eImageUser = findViewById(R.id.Image_infoAccount);
        createDayUser = findViewById(R.id.createDay_infoAccount);

        idUser = sessionManager.getID();

        readAllData();
        changeBtn();
        btnBack();
    }

    private void btnBack() {

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserInformation.super.onBackPressed();
            }
        });

    }

    private void changeBtn() {

        changeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), EditProfile.class);
                startActivity(intent);
            }
        });

    }

    private void readAllData() {

        Cursor cursor = db.readAllData_User(Integer.valueOf(idUser));
        while (cursor.moveToNext()){

            eUserName.setText(cursor.getString(1));
            eName.setText(cursor.getString(3));
            eEmail.setText(cursor.getString(4));
            ePhone.setText(cursor.getString(5));
            eGender.setText(cursor.getString(6));
            eDate.setText(cursor.getString(7));
            if(cursor.getBlob(8) == null){
                eImageUser.setImageResource(R.drawable.account);
            }else{
                byte[] image = cursor.getBlob(8);
                Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
                eImageUser.setImageBitmap(bitmap);
            }
            createDayUser.setText(cursor.getString(9));
        }

    }
}