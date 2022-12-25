package com.example.projectandroid.User.MProduct.AddProduct;

import static com.example.projectandroid.User.DashBoard.idUser;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectandroid.HelperClasses.Product.ListProduct.GetImageProductClass;
import com.example.projectandroid.HelperClasses.SqlLite.SqlDatabaseHelper;
import com.example.projectandroid.ProgessLoading;
import com.example.projectandroid.R;
import com.example.projectandroid.SessionManager;
import com.example.projectandroid.User.MProduct.AddTypeProduct.AddTypeProduct;
import com.google.android.material.textfield.TextInputEditText;

import java.io.IOException;
import java.util.ArrayList;

public class AddProduct extends AppCompatActivity {

    ArrayList<String> itemTypeProduct;
    AutoCompleteTextView TypeProduct;
    ArrayAdapter adapterItemTypeProduct;

    ImageView btnBack, ImageProduct;
    Button ConfirmBtn, choseImageBtn;
    TextInputEditText NameProduct, QualityProduct, UnitProduct, PriceProduct;

    SqlDatabaseHelper db;

    ActivityResultLauncher<String> getImage;
    ActivityResultLauncher<Intent> getCamera;

    Uri imageFilePath,camUri;
    Bitmap imageToStore;

    Button ConfirmBtnDia, CancelBtnDia;
    TextView ContentDia;
    Dialog dialog;

    Dialog pickImageDialog;
    ImageView GalleryOpen,CameraOpen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_add_product);

        db = new SqlDatabaseHelper(AddProduct.this);

        final ProgessLoading progessLoading = new ProgessLoading(this);

        getImage = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                try {
                    imageFilePath = result;
                    imageToStore = MediaStore.Images.Media.getBitmap(getContentResolver(),imageFilePath);

                    ImageProduct.setImageBitmap(imageToStore);
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
                    ImageProduct.setImageURI(camUri);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });

        btnBack = findViewById(R.id.back_btn);
        ConfirmBtn = findViewById(R.id.confirm_btn);
        TypeProduct = findViewById(R.id.type_product_addProduct);
        NameProduct = findViewById(R.id.name_product_addProduct);
        QualityProduct = findViewById(R.id.quality_product_addProduct);
        UnitProduct = findViewById(R.id.unit_product_addProduct);
        PriceProduct = findViewById(R.id.price_product_addProduct);
        ImageProduct = findViewById(R.id.image_product_addProduct);
        choseImageBtn = findViewById(R.id.chose_image_btn_addProduct);



        ConfirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String typeProductName = TypeProduct.getText().toString();

                Cursor cursor = db.getTypeProductID_Product(typeProductName, Integer.valueOf(idUser));
                if (cursor.getCount() == 0) {

                    Toast.makeText(AddProduct.this, "Vui Lòng Nhập Loại Sản Phẩm", Toast.LENGTH_SHORT).show();

                } else {

                    Integer gIDTypeProduct = null;
                    String gProductName = NameProduct.getText().toString();
                    String gProductQuality = QualityProduct.getText().toString();
                    String gProductUnit = UnitProduct.getText().toString();
                    String gProductPrice = PriceProduct.getText().toString();
                    while (cursor.moveToNext()) {
                        gIDTypeProduct = cursor.getInt(0);
                    }
                    if (gIDTypeProduct == 0 || gProductName.isEmpty() || gProductQuality.isEmpty() || gProductUnit.isEmpty() || gProductPrice.isEmpty() || ImageProduct.getDrawable() == null || imageToStore == null) {

                        Toast.makeText(AddProduct.this, "Vui Lòng Nhập Đầy Đủ Thông Tin", Toast.LENGTH_SHORT).show();

                    } else {

                        Boolean resultNameProduct = db.checkNameProduct_Product(gProductName, Integer.valueOf(idUser));
                        if (resultNameProduct == false) {

                            Boolean resultInsertData = db.insertData_Product(gProductName, gProductQuality, gProductUnit, gProductPrice, new GetImageProductClass(imageToStore), gIDTypeProduct, Integer.valueOf(idUser));
                            if (resultInsertData == true) {

                                progessLoading.show();
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        Intent intent = new Intent(AddProduct.this, CompleteAddProduct.class);
                                        startActivity(intent);
                                        progessLoading.dismiss();
                                    }
                                }, 2000);

                            } else {

                                progessLoading.show();
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(AddProduct.this, "Thêm Sản Phẩm Thất Bại", Toast.LENGTH_SHORT).show();
                                        progessLoading.dismiss();
                                    }
                                }, 2000);

                            }
                        } else {

                            progessLoading.show();
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(AddProduct.this, "Tên Sản Phẩm Đã Tồn Tại", Toast.LENGTH_SHORT).show();
                                    progessLoading.dismiss();
                                    NameProduct.forceLayout();
                                }
                            }, 2000);

                        }
                    }
                }
            }
        });

        TypeProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadDataTypeProduct();
            }
        });


        btnBack();
        loadDataTypeProduct();
        choseImageBtn();
        ShowDiaLog();
        ShowPickImageDiaLog();

    }

    private void loadDataTypeProduct() {

        Cursor cursor = db.readTypeProduct_Product(Integer.valueOf(idUser));

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

    private void choseImageBtn() {

        choseImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickImageDialog.show();
            }
        });
    }

    public void ShowDiaLog() {

        dialog = new Dialog(AddProduct.this);
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

    public void ShowPickImageDiaLog() {

        pickImageDialog = new Dialog(AddProduct.this);
        pickImageDialog.setContentView(R.layout.custom_dialog_pick_image);
        pickImageDialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.bg_dialog));
        pickImageDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        pickImageDialog.setCancelable(false);

        CameraOpen = pickImageDialog.findViewById(R.id.cameraBtn);
        GalleryOpen = pickImageDialog.findViewById(R.id.galleryBtn);

        GalleryOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getImage.launch("image/*");
                pickImageDialog.dismiss();
            }
        });

        CameraOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.TITLE, "New Picture");
                values.put(MediaStore.Images.Media.DESCRIPTION, "From Camera");
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE_SECURE);
                camUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, camUri);
                getCamera.launch(intent);
                pickImageDialog.dismiss();
            }
        });

    }

    private void btnBack() {

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddProduct.super.onBackPressed();
            }
        });
    }
}