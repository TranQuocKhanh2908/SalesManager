package com.example.projectandroid.User.MProduct.ListProduct;

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
import android.graphics.BitmapFactory;
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
import android.widget.Toast;

import com.example.projectandroid.HelperClasses.Product.ListProduct.GetImageProductClass;
import com.example.projectandroid.HelperClasses.SqlLite.SqlDatabaseHelper;
import com.example.projectandroid.ProgessLoading;
import com.example.projectandroid.R;
import com.example.projectandroid.User.Product;
import com.google.android.material.textfield.TextInputEditText;

import java.io.IOException;
import java.util.ArrayList;

public class UpdateProduct extends AppCompatActivity {

    ArrayList<String> itemTypeProduct;
    AutoCompleteTextView TypeProduct;
    ArrayAdapter adapterItemTypeProduct;

    ImageView btnBack, ImageProduct;
    Button ConfirmBtn, choseImageBtn;
    TextInputEditText NameProduct, QualityProduct, UnitProduct, PriceProduct;

    SqlDatabaseHelper db;
    int id_Product;

    ActivityResultLauncher<String> getImage;
    ActivityResultLauncher<Intent> getCamera;

    Uri imageFilePath,camUri;
    Bitmap imageToStore;

    ProgessLoading progessLoading;

    Dialog pickImageDialog;
    ImageView GalleryOpen,CameraOpen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_product);

        db = new SqlDatabaseHelper(this);

        progessLoading = new ProgessLoading(this);

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
        TypeProduct = findViewById(R.id.type_product_updateProduct);
        NameProduct = findViewById(R.id.name_product_updateProduct);
        QualityProduct = findViewById(R.id.quality_product_updateProduct);
        UnitProduct = findViewById(R.id.unit_product_updateProduct);
        PriceProduct = findViewById(R.id.price_product_updateProduct);
        ImageProduct = findViewById(R.id.image_product_updateProduct);
        choseImageBtn = findViewById(R.id.chose_image_btn_updateProduct);

        Intent i = getIntent();
        String id = i.getStringExtra("Id_Product");
        id_Product = Integer.parseInt(id);

        ConfirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String typeProductName = TypeProduct.getText().toString();

                Cursor cursor = db.getTypeProductID_Product(typeProductName, Integer.valueOf(idUser));
                if (cursor.getCount() == 0) {

                    Toast.makeText(UpdateProduct.this, "Vui Lòng Nhập Loại Sản Phẩm", Toast.LENGTH_SHORT).show();

                } else {

                    Integer gIDTypeProduct = null;
                    String gProductName = NameProduct.getText().toString();
                    String gProductQuality = QualityProduct.getText().toString();
                    String gProductUnit = UnitProduct.getText().toString();
                    String gProductPrice = PriceProduct.getText().toString();
                    String gNameProductExist = null;
                    while (cursor.moveToNext()) {
                        gIDTypeProduct = cursor.getInt(0);
                    }
                    if (gIDTypeProduct == 0 || gProductName.isEmpty() || gProductQuality.isEmpty() || gProductUnit.isEmpty() || gProductPrice.isEmpty()) {

                        Toast.makeText(UpdateProduct.this, "Vui Lòng Nhập Đầy Đủ Thông Tin", Toast.LENGTH_SHORT).show();

                    } else {

                        if (imageToStore == null) {

                            Cursor cursorImage = db.checkImageProductExits_Product(id_Product);
                            while (cursorImage.moveToNext()) {
                                byte[] image = cursorImage.getBlob(0);
                                Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
                                imageToStore = bitmap;
                            }
                        } else {
                            Cursor cursorReadNameProductExist = db.readNameProductExist_Product(id_Product);
                            while (cursorReadNameProductExist.moveToNext()) {
                                gNameProductExist = cursorReadNameProductExist.getString(0);
                            }
                            if (gProductName.equals(gNameProductExist)) {

                                Boolean resultUpdateData = db.updateData_Product(id_Product, gProductName, gProductQuality, gProductUnit, gProductPrice, new GetImageProductClass(imageToStore), gIDTypeProduct);
                                if (resultUpdateData == true) {

                                    progessLoading.show();
                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            Intent intent = new Intent(UpdateProduct.this, Product.class);
                                            startActivity(intent);
                                            Toast.makeText(UpdateProduct.this, "Sửa Sản Phẩm Thành Công", Toast.LENGTH_SHORT).show();
                                            progessLoading.dismiss();
                                        }
                                    }, 2000);

                                } else {

                                    progessLoading.show();
                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(UpdateProduct.this, "Sửa Sản Phẩm Thất Bại", Toast.LENGTH_SHORT).show();
                                            progessLoading.dismiss();
                                        }
                                    }, 2000);
                                }
                            } else {
                                Boolean resultNameProduct = db.checkNameProduct_Product(gProductName, Integer.valueOf(idUser));
                                if (resultNameProduct == false) {

                                    Boolean resultUpdateData = db.updateData_Product(id_Product, gProductName, gProductQuality, gProductUnit, gProductPrice, new GetImageProductClass(imageToStore), gIDTypeProduct);
                                    if (resultUpdateData == true) {

                                        progessLoading.show();
                                        new Handler().postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                Intent intent = new Intent(UpdateProduct.this, Product.class);
                                                startActivity(intent);
                                                Toast.makeText(UpdateProduct.this, "Sửa Sản Phẩm Thành Công", Toast.LENGTH_SHORT).show();
                                                progessLoading.dismiss();
                                            }
                                        }, 2000);

                                    } else {

                                        progessLoading.show();
                                        new Handler().postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                Toast.makeText(UpdateProduct.this, "Sửa Sản Phẩm Thất Bại", Toast.LENGTH_SHORT).show();
                                                progessLoading.dismiss();
                                            }
                                        }, 2000);

                                    }
                                } else {

                                    progessLoading.show();
                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(UpdateProduct.this, "Tên Sản Phẩm Đã Tồn Tại", Toast.LENGTH_SHORT).show();
                                            progessLoading.dismiss();
                                            NameProduct.forceLayout();
                                        }
                                    }, 2000);
                                }
                            }
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

        readAllData();
        btnBack();
        choseImageBtn();
        ShowPickImageDiaLog();
        loadDataTypeProduct();
    }
    private void readAllData() {
        Cursor cursor = db.ReadAllData_Product(id_Product);
        if(cursor.getCount() == 0){
            Toast.makeText(this, "Không Có Id Sản Phẩm", Toast.LENGTH_SHORT).show();
        }else{
            while (cursor.moveToNext()){
                TypeProduct.setText(cursor.getString(1));
                NameProduct.setText(cursor.getString(2));
                QualityProduct.setText(cursor.getString(3));
                UnitProduct.setText(cursor.getString(4));
                PriceProduct.setText(cursor.getString(5));
                byte[] image = cursor.getBlob(6);
                Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
                ImageProduct.setImageBitmap(bitmap);
            }

        }
    }

    private void loadDataTypeProduct() {

        Cursor cursor = db.readTypeProduct_Product(Integer.valueOf(idUser));

        itemTypeProduct = new ArrayList<>();
        if(cursor.getCount() == 0){

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

    public void ShowPickImageDiaLog() {

        pickImageDialog = new Dialog(UpdateProduct.this);
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
                UpdateProduct.super.onBackPressed();
            }
        });

    }
}