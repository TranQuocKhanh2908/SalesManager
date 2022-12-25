package com.example.projectandroid.User.Profile;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.cardview.widget.CardView;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.projectandroid.HelperClasses.Product.ListProduct.GetImageProductClass;
import com.example.projectandroid.HelperClasses.Profile.GetImageUserClass;
import com.example.projectandroid.HelperClasses.SqlLite.SqlDatabaseHelper;
import com.example.projectandroid.ProgessLoading;
import com.example.projectandroid.R;
import com.example.projectandroid.SessionManager;
import com.example.projectandroid.common.LoginSignUp.Login;
import com.example.projectandroid.common.LoginSignUp.SignUp;
import com.example.projectandroid.common.LoginSignUp.StartUpScreen;
import com.example.projectandroid.common.LoginSignUp.VerifySignUp;
import com.google.android.material.textfield.TextInputEditText;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class EditProfile extends AppCompatActivity {

    String[] itemGender = {"Nam", "Nữ", "Khác"};
    AutoCompleteTextView eGender;
    ArrayAdapter<String> adapterItemGender;

    ImageView btnBack, eImageUser;
    TextInputEditText eName, eUserName, eEmail, ePhone, eDate, DiaVerifyCode, DiaVerifyCodeDelete;
    Button submitBtn, deleteBtn, ConfirmBtnDiaDelete, CancelBtnDiaDelete, ConfirmBtnVerifyCode, CancelBtnVerifyCode;
    TextView createDayUser;
    CardView ChangeImage;

    Dialog dialogDelete, dialogVerifyCode;

    ActivityResultLauncher<String> getImage;
    ActivityResultLauncher<Intent> getCamera;

    Uri imageFilePath,camUri;
    Bitmap imageToStore;

    SqlDatabaseHelper db;
    SessionManager sessionManager;
    ProgessLoading progessLoading;

    String idUser, gName, gUserName, gEmail, gPhone, gDate, gGender;

    Integer verifyCode, verifyCodeDeleteAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_edit_profile);

        db = new SqlDatabaseHelper(this);

        sessionManager = new SessionManager(this);

        progessLoading = new ProgessLoading(this);

        getImage = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                try {
                    imageFilePath = result;
                    imageToStore = MediaStore.Images.Media.getBitmap(getContentResolver(),imageFilePath);

                    eImageUser.setImageBitmap(imageToStore);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        getCamera = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {

                try {
                    imageToStore = MediaStore.Images.Media.getBitmap(getContentResolver(),camUri);
                    eImageUser.setImageURI(camUri);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });

        btnBack = findViewById(R.id.back_btn);
        submitBtn = findViewById(R.id.btn_Edit_profile);
        ChangeImage = findViewById(R.id.change_image_user_editProfile);
        deleteBtn = findViewById(R.id.delete_account_editProfile);
        eName = findViewById(R.id.name_user_editProfile);
        eUserName = findViewById(R.id.userName_user_editProfile);
        eEmail = findViewById(R.id.email_user_editProfile);
        ePhone = findViewById(R.id.phone_user_editProfile);
        eDate = findViewById(R.id.age_user_editProfile);
        eGender = findViewById(R.id.gender_user_editProfile);
        eImageUser = findViewById(R.id.Image_profile);
        createDayUser = findViewById(R.id.createDay_User);
        adapterItemGender = new ArrayAdapter<String>(this, R.layout.list_item_dropmenu, itemGender);
        eGender.setAdapter(adapterItemGender);

        idUser = sessionManager.getID();

        if(idUser.equals("0") || idUser.equals("")){

            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
            finish();

        }

        eGender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapterItemGender = new ArrayAdapter<String>(EditProfile.this, R.layout.list_item_dropmenu, itemGender);
                eGender.setAdapter(adapterItemGender);
            }
        });

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String gExistsUserName = null;
                String gExistsEmail = null;
                String gExistsPhone = null;

                Cursor cursorReadData = db.readAllData_User(Integer.valueOf(idUser));
                while (cursorReadData.moveToNext()) {

                    gExistsUserName = cursorReadData.getString(1);
                    gExistsEmail = cursorReadData.getString(4);
                    gExistsPhone = cursorReadData.getString(5);

                }

                Date gCurrentDay = null;
                Date gDATE = null;
                SimpleDateFormat dateFormat = new SimpleDateFormat("MM/d/yyyy");
                Calendar c = Calendar.getInstance();
                String Day = eDate.getText().toString();
                String gCDate = dateFormat.format(c.getTime());
                try {
                    gCurrentDay = dateFormat.parse(gCDate);
                    gDATE = dateFormat.parse(Day);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                gName = eName.getText().toString();
                gUserName = eUserName.getText().toString();
                gEmail = eEmail.getText().toString();
                gPhone = ePhone.getText().toString();
                gDate = eDate.getText().toString();
                gGender = eGender.getText().toString();

                if (gName.isEmpty() || gUserName.isEmpty() || gEmail.isEmpty() || gPhone.isEmpty() || gDate.isEmpty() || gGender.isEmpty()) {

                    Toast.makeText(EditProfile.this, "Vui Lòng Nhập Đầy Đủ Thông Tin", Toast.LENGTH_SHORT).show();

                } else {

                    if (Patterns.EMAIL_ADDRESS.matcher(gEmail).matches()) {

                        if (gDATE.before(gCurrentDay)) {

                            if (gUserName.equals(gExistsUserName) && gEmail.equals(gExistsEmail) && gPhone.equals(gExistsPhone)) {

                                insertIfImageNull();

                            } else {

                                if (gUserName.compareTo(gExistsUserName) != 1 && gEmail.equals(gExistsEmail) && gPhone.equals(gExistsPhone)) {

                                    Boolean resultCheckExistUserName = db.checkUsernameExist_Users(gUserName);
                                    if (resultCheckExistUserName == true) {

                                        Toast.makeText(EditProfile.this, "Tên Đăng Nhập Đã Tồn Tại \n Vui Lòng Nhập Tên Đăng Nhập Khác", Toast.LENGTH_SHORT).show();

                                    } else {

                                        insertIfImageNull();

                                    }
                                } else {

                                    if (gUserName.equals(gExistsUserName) && gEmail.compareTo(gExistsEmail) != 1 && gPhone.equals(gExistsPhone)) {

                                        Boolean resultCheckExistEmail = db.checkEmailExist_Users(gEmail);
                                        if (resultCheckExistEmail == true) {

                                            Toast.makeText(EditProfile.this, "Email Đã Tồn Tại \n Vui Lòng Nhập Email Khác", Toast.LENGTH_SHORT).show();

                                        } else {

                                            progessLoading.show();
                                            String finalGExistsEmail3 = gExistsEmail;
                                            new Handler().postDelayed(new Runnable() {
                                                @Override
                                                public void run() {
                                                    sendVerifyEmail(finalGExistsEmail3);
                                                    dialogVerifyCode.show();
                                                    progessLoading.dismiss();
                                                }
                                            }, 2000);
                                        }
                                    } else {

                                        if (gUserName.equals(gExistsUserName) && gEmail.equals(gExistsEmail) && gPhone.compareTo(gExistsPhone) != 1) {

                                            Boolean resultCheckExistPhone = db.checkPhoneExist_Users(gPhone);
                                            if (resultCheckExistPhone == true) {

                                                Toast.makeText(EditProfile.this, "Số Điện Thoại Đã Tồn Tại \n Vui Lòng Nhập Số Điện Thoại Khác", Toast.LENGTH_SHORT).show();

                                            } else {

                                                insertIfImageNull();

                                            }
                                        } else {

                                            if (gUserName.compareTo(gExistsUserName) != 1 && gEmail.compareTo(gExistsEmail) != 1 && gPhone.equals(gExistsPhone)) {

                                                Boolean resultCheckExistUserName = db.checkUsernameExist_Users(gUserName);
                                                if (resultCheckExistUserName == true) {

                                                    Toast.makeText(EditProfile.this, "Tên Đăng Nhập Đã Tồn Tại \n Vui Lòng Nhập Tên Đăng Nhập Khác", Toast.LENGTH_SHORT).show();

                                                } else {

                                                    Boolean resultCheckExistEmail = db.checkEmailExist_Users(gEmail);
                                                    if (resultCheckExistEmail == true) {

                                                        Toast.makeText(EditProfile.this, "Email Đã Tồn Tại \n Vui Lòng Nhập Email Khác", Toast.LENGTH_SHORT).show();

                                                    } else {

                                                        progessLoading.show();
                                                        String finalGExistsEmail = gExistsEmail;
                                                        new Handler().postDelayed(new Runnable() {
                                                            @Override
                                                            public void run() {
                                                                sendVerifyEmail(finalGExistsEmail);
                                                                dialogVerifyCode.show();
                                                                progessLoading.dismiss();
                                                            }
                                                        }, 2000);
                                                    }
                                                }
                                            } else {

                                                if (gUserName.compareTo(gExistsUserName) != 1 && gEmail.equals(gExistsEmail) && gPhone.compareTo(gExistsPhone) != 1) {

                                                    Boolean resultCheckExistUserName = db.checkUsernameExist_Users(gUserName);
                                                    if (resultCheckExistUserName == true) {

                                                        Toast.makeText(EditProfile.this, "Tên Đăng Nhập Đã Tồn Tại \n Vui Lòng Nhập Tên Đăng Nhập Khác", Toast.LENGTH_SHORT).show();

                                                    } else {

                                                        Boolean resultCheckExistPhone = db.checkPhoneExist_Users(gPhone);
                                                        if (resultCheckExistPhone == true) {

                                                            Toast.makeText(EditProfile.this, "Số Điện Thoại Đã Tồn Tại \n Vui Lòng Nhập Số Điện Thoại Khác", Toast.LENGTH_SHORT).show();

                                                        } else {

                                                            insertIfImageNull();

                                                        }
                                                    }
                                                } else {

                                                    if (gUserName.equals(gExistsUserName) && gEmail.compareTo(gExistsEmail) != 1 && gPhone.compareTo(gExistsPhone) != 1) {

                                                        Boolean resultCheckExistEmail = db.checkEmailExist_Users(gEmail);
                                                        if (resultCheckExistEmail == true) {

                                                            Toast.makeText(EditProfile.this, "Email Đã Tồn Tại \n Vui Lòng Nhập Email Khác", Toast.LENGTH_SHORT).show();

                                                        } else {

                                                            Boolean resultCheckExistPhone = db.checkPhoneExist_Users(gPhone);
                                                            if (resultCheckExistPhone == true) {

                                                                Toast.makeText(EditProfile.this, "Số Điện Thoại Đã Tồn Tại \n Vui Lòng Nhập Số Điện Thoại Khác", Toast.LENGTH_SHORT).show();

                                                            } else {

                                                                progessLoading.show();
                                                                String finalGExistsEmail1 = gExistsEmail;
                                                                new Handler().postDelayed(new Runnable() {
                                                                    @Override
                                                                    public void run() {
                                                                        sendVerifyEmail(finalGExistsEmail1);
                                                                        dialogVerifyCode.show();
                                                                        progessLoading.dismiss();
                                                                    }
                                                                }, 2000);

                                                            }
                                                        }
                                                    } else {

                                                        if (gUserName.compareTo(gExistsUserName) != 1 && gEmail.compareTo(gExistsEmail) != 1 && gPhone.compareTo(gExistsPhone) != 1) {

                                                            Boolean resultCheckExistUserName = db.checkUsernameExist_Users(gUserName);
                                                            if (resultCheckExistUserName == true) {

                                                                Toast.makeText(EditProfile.this, "Tên Đăng Nhập Đã Tồn Tại \n Vui Lòng Nhập Tên Đăng Nhập Khác", Toast.LENGTH_SHORT).show();

                                                            } else {

                                                                Boolean resultCheckExistEmail = db.checkEmailExist_Users(gEmail);
                                                                if (resultCheckExistEmail == true) {

                                                                    Toast.makeText(EditProfile.this, "Email Đã Tồn Tại \n Vui Lòng Nhập Email Khác", Toast.LENGTH_SHORT).show();

                                                                } else {

                                                                    Boolean resultCheckExistPhone = db.checkPhoneExist_Users(gPhone);
                                                                    if (resultCheckExistPhone == true) {

                                                                        Toast.makeText(EditProfile.this, "Số Điện Thoại Đã Tồn Tại \n Vui Lòng Nhập Số Điện Thoại Khác", Toast.LENGTH_SHORT).show();

                                                                    } else {

                                                                        progessLoading.show();
                                                                        String finalGExistsEmail2 = gExistsEmail;
                                                                        new Handler().postDelayed(new Runnable() {
                                                                            @Override
                                                                            public void run() {
                                                                                sendVerifyEmail(finalGExistsEmail2);
                                                                                dialogVerifyCode.show();
                                                                                progessLoading.dismiss();
                                                                            }
                                                                        }, 2000);

                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        } else {

                            Toast.makeText(EditProfile.this, "Vui Lòng Nhập Tuổi Hợp Lệ", Toast.LENGTH_SHORT).show();

                        }

                    } else {

                        Toast.makeText(EditProfile.this, "Vui Lòng Nhập Đúng Định Dạng Email", Toast.LENGTH_SHORT).show();

                    }
                }
            }
        });

        VerifyDeleteCodeDiaLog();
        btnBack();
        deleteBtn();
        ChangeImage();
        eDate();
        readAllData();
        deleteBtn();
        VerifyCodeDiaLog();

    }

    private void insertIfImageNull(){

        if (imageToStore == null) {

            Cursor cursorCheck = db.readImageExists_User(Integer.valueOf(idUser));
            while (cursorCheck.moveToNext()) {
                byte[] CheckImage = cursorCheck.getBlob(0);
                if (CheckImage == null) {

                    imageToStore = BitmapFactory.decodeResource(getResources(), R.drawable.account);
                    Boolean resultUpdateData = db.updateData_Users(Integer.parseInt(idUser), gUserName, gName, gEmail, gPhone, gGender, gDate, new GetImageUserClass(imageToStore));
                    if (resultUpdateData == true) {

                        progessLoading.show();

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = new Intent(EditProfile.this, Profile.class);
                                startActivity(intent);
                                Toast.makeText(EditProfile.this, "Sửa Hồ Sơ Thành Công", Toast.LENGTH_SHORT).show();
                                progessLoading.dismiss();
                            }
                        }, 2000);
                    } else {

                        Toast.makeText(EditProfile.this, "Sửa Hồ Sơ Thất Bại", Toast.LENGTH_SHORT).show();

                    }
                } else {

                    Cursor cursor = db.readImageExists_User(Integer.valueOf(idUser));
                    while (cursor.moveToNext()) {
                        byte[] image = cursor.getBlob(0);
                        Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
                        imageToStore = bitmap;
                    }
                    Boolean resultUpdateData = db.updateData_Users(Integer.parseInt(idUser), gUserName, gName, gEmail, gPhone, gGender, gDate, new GetImageUserClass(imageToStore));
                    if (resultUpdateData == true) {

                        progessLoading.show();

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = new Intent(EditProfile.this, Profile.class);
                                startActivity(intent);
                                Toast.makeText(EditProfile.this, "Sửa Hồ Sơ Thành Công", Toast.LENGTH_SHORT).show();
                                progessLoading.dismiss();
                            }
                        }, 2000);
                    } else {

                        Toast.makeText(EditProfile.this, "Sửa Hồ Sơ Thất Bại", Toast.LENGTH_SHORT).show();

                    }
                }
            }
        } else {

            Boolean resultUpdateData = db.updateData_Users(Integer.parseInt(idUser), gUserName, gName, gEmail, gPhone, gGender, gDate, new GetImageUserClass(imageToStore));
            if (resultUpdateData == true) {

                progessLoading.show();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(EditProfile.this, Profile.class);
                        startActivity(intent);
                        Toast.makeText(EditProfile.this, "Sửa Hồ Sơ Thành Công", Toast.LENGTH_SHORT).show();
                        progessLoading.dismiss();
                    }
                }, 2000);
            } else {

                Toast.makeText(EditProfile.this, "Sửa Hồ Sơ Thất Bại", Toast.LENGTH_SHORT).show();

            }
        }
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

    private void eDate() {

        eDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PickDate();
            }
        });

    }

    private void PickDate(){
        String Day = eDate.getText().toString();
        String splitText[] = Day.split("/");
        int day = Integer.parseInt(splitText[1]);
        int month = Integer.parseInt(splitText[0]) - 1;
        int year = Integer.parseInt(splitText[2]);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                i1 = i1 + 1;
                String date = i1 + "/" + i2 + "/" + i;
                eDate.setText(date);
            }
        },year,month,day);
        datePickerDialog.show();

    }

    private void deleteBtn() {

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progessLoading.show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Cursor cursor = db.readAllData_User(Integer.valueOf(idUser));
                        String gE = null;
                        while (cursor.moveToNext()) {
                            gE = cursor.getString(4);
                        }
                        sendVerifyEmailDelete(gE);
                        dialogDelete.show();
                        progessLoading.dismiss();
                    }
                },2000);

            }
        });

    }

    private void ChangeImage() {

        ChangeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(EditProfile.this, view);
                popupMenu.inflate(R.menu.edit_image_menu);
                popupMenu.show();

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()){

                            case R.id.take_picture:
                                ContentValues values = new ContentValues();
                                values.put(MediaStore.Images.Media.TITLE, "New Picture");
                                values.put(MediaStore.Images.Media.DESCRIPTION, "From Camera");
                                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE_SECURE);
                                camUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                                intent.putExtra(MediaStore.EXTRA_OUTPUT, camUri);
                                getCamera.launch(intent);
                                break;

                            case R.id.chose_picture:
                                getImage.launch("image/*");
                        }
                        return false;
                    }
                });
            }
        });
    }

    public void VerifyCodeDiaLog() {

        dialogVerifyCode = new Dialog(EditProfile.this);
        dialogVerifyCode.setContentView(R.layout.custom_dialog_verifycode);
        dialogVerifyCode.getWindow().setBackgroundDrawable(getDrawable(R.drawable.bg_dialog));
        dialogVerifyCode.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialogVerifyCode.setCancelable(false);

        ConfirmBtnVerifyCode = dialogVerifyCode.findViewById(R.id.Confirm_dialogVerify_btn);
        CancelBtnVerifyCode = dialogVerifyCode.findViewById(R.id.Cancel_dialogVerify_btn);
        DiaVerifyCode = dialogVerifyCode.findViewById(R.id.verify_code);

        ConfirmBtnVerifyCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String gVerifyCode = DiaVerifyCode.getText().toString();
                if (gVerifyCode.equals(verifyCode.toString())) {
                    dialogVerifyCode.dismiss();
                    insertIfImageNull();
                } else {
                    dialogVerifyCode.dismiss();
                    progessLoading.show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(EditProfile.this, "Sai Mã", Toast.LENGTH_SHORT).show();
                            progessLoading.dismiss();
                        }
                    }, 2000);
                }
            }
        });

        CancelBtnVerifyCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogVerifyCode.dismiss();
            }
        });

    }

    public void VerifyDeleteCodeDiaLog() {

        dialogDelete = new Dialog(EditProfile.this);
        dialogDelete.setContentView(R.layout.custom_dialog_delete_account);
        dialogDelete.getWindow().setBackgroundDrawable(getDrawable(R.drawable.bg_dialog));
        dialogDelete.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialogDelete.setCancelable(false);

        ConfirmBtnDiaDelete = dialogDelete.findViewById(R.id.Confirm_dialogDelete_btn);
        CancelBtnDiaDelete = dialogDelete.findViewById(R.id.Cancel_dialogDelete_btn);
        DiaVerifyCodeDelete = dialogDelete.findViewById(R.id.verify_code_delete);

        ConfirmBtnDiaDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String gVerifyCode = DiaVerifyCodeDelete.getText().toString();
                if(gVerifyCode.equals(verifyCodeDeleteAccount.toString())) {
                    Boolean resultDeleteData = db.deleteAccount_User(Integer.valueOf(idUser));
                    if (resultDeleteData == true) {
                        dialogDelete.dismiss();
                        progessLoading.show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = new Intent(getApplicationContext(), StartUpScreen.class);
                                startActivity(intent);
                                sessionManager.setLogin(false);
                                sessionManager.setId("0");
                                progessLoading.dismiss();
                                Toast.makeText(EditProfile.this, "Xóa Tài Khoản Thành Công", Toast.LENGTH_SHORT).show();
                            }
                        },2000);

                    } else {
                        dialogDelete.dismiss();
                        progessLoading.show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(EditProfile.this, "Xóa Tài Khoản Thất Bại", Toast.LENGTH_SHORT).show();
                                progessLoading.dismiss();
                            }
                        },2000);
                    }
                }else{
                    dialogDelete.dismiss();
                    progessLoading.show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(EditProfile.this, "Sai Mã", Toast.LENGTH_SHORT).show();
                            progessLoading.dismiss();
                        }
                    },2000);
                }
            }
        });

        CancelBtnDiaDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogDelete.dismiss();
            }
        });

    }

    private void sendVerifyEmail(String email){
        Random random = new Random();
        verifyCode = random.nextInt(8999) + 1000;
        String url = "https://sendemailprojectandroid.000webhostapp.com/sendCodeChangeEmail.php";
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("email",email);
                params.put("code", String.valueOf(verifyCode));
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void sendVerifyEmailDelete(String email){
        Random random = new Random();
        verifyCodeDeleteAccount = random.nextInt(8999) + 1000;
        String url = "https://sendemailprojectandroid.000webhostapp.com/sendCodeDeleteAccount.php";
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("email",email);
                params.put("code", String.valueOf(verifyCodeDeleteAccount));
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void btnBack() {

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditProfile.super.onBackPressed();
            }
        });
    }
    
}