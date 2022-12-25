package com.example.projectandroid.HelperClasses.SqlLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;

import androidx.annotation.Nullable;

import com.example.projectandroid.HelperClasses.Product.ListProduct.GetImageProductClass;
import com.example.projectandroid.HelperClasses.Profile.GetImageUserClass;

import java.io.ByteArrayOutputStream;

public class SqlDatabaseHelper extends SQLiteOpenHelper {

    //Database
    private Context context;
    private static final String DATABASE_NAME = "MyDatabase.db";
    private static final int DATABASE_VERSION = 3;
    //Database


    //Login_Signup
    private static final String TABLE_USERS = "USERS";
    private static final String COLUMN_ID_USERS = "ID_Users";
    private static final String COLUMN_USERNAME_USERS = "UserName";
    private static final String COLUMN_PASSWORD_USERS = "Password";
    private static final String COLUMN_NAME_USERS = "Name";
    private static final String COLUMN_EMAIL_USERS = "Email";
    private static final String COLUMN_GENDER_USERS = "Gender";
    private static final String COLUMN_AGE_USERS = "Age";
    private static final String COLUMN_PHONE_USERS = "Phone";
    private static final String COLUMN_IMAGE_USERS = "Image";
    private static final String COLUMN_CREATE_DAY_USERS = "Create_Day";

    private ByteArrayOutputStream byteArrayOutputStreamUser;
    private byte[] imageUserByte;
    //Login_Signup


    //TypeProduct
    private static final String TABLE_TYPE_PRODUCT = "TYPE_PRODUCT";
    private static final String COLUMN_ID_TYPE_PRODUCT = "ID_Type_Product";
    private static final String COLUMN_TYPE_PRODUCT_NAME = "Type_Product_Name";
    private static final String COLUMN_TYPE_PRODUCT_DESCRIPTION = "Type_Product_Description";
    private static final String F_TYPE_PRODUCT_COLUMN_ID_USER = "ID_User_Type_Product";
    //TypeProduct


    //Product
    private static final String TABLE_PRODUCT = "PRODUCT";
    private static final String COLUMN_ID_PRODUCT = "ID_Product";
    private static final String COLUMN_PRODUCT_NAME = "Product_Name";
    private static final String COLUMN_PRODUCT_QUALITY = "Product_QUALITY";
    private static final String COLUMN_PRODUCT_UNIT = "Product_UNIT";
    private static final String COLUMN_PRODUCT_PRICE = "Product_PRICE";
    private static final String COLUMN_PRODUCT_IMAGE = "Product_IMAGE";
    private static final String F_PRODUCT_COLUMN_ID_TYPE_PRODUCT = "ID_TypeProduct_Product";
    private static final String F_PRODUCT_COLUMN_ID_USER = "ID_User_Product";

    private ByteArrayOutputStream byteArrayOutputStreamProduct;
    private byte[] imageProductByte;
    //Product

    //Bill
    private static final String TABLE_BILL = "BILL";
    private static final String COLUMN_ID_BILL = "ID_Bill";
    private static final String COLUMN_BILL_QUALITY = "Bill_Product_Quality";
    private static final String COLUMN_BILL_TOTAL_PRICE = "Bill_Total_Price";
    private static final String COLUMN_BILL_CREATE_DAY = "Bill_Create_Day";
    private static final String COLUMN_BILL_CREATE_TIME = "Bill_Create_Time";
    private static final String F_BILL_COLUMN_ID_TYPE_PRODUCT = "ID_TypeProduct_Bill";
    private static final String F_BILL_COLUMN_ID_PRODUCT = "ID_Product_Bill";
    private static final String F_BILL_COLUMN_ID_USER = "ID_User_Bill";
    //Bill

    //Promotion
    private static final String TABLE_PROMOTION = "PROMOTION";
    private static final String COLUMN_ID_PROMOTION = "ID_PROMOTION";
    private static final String COLUMN_PROMOTION_PERCENT = "PROMOTION_Percent";
    private static final String COLUMN_PROMOTION_PRICE_AFTER = "PROMOTION_Price_After";
    private static final String COLUMN_PROMOTION_START_DAY = "PROMOTION_Start_Day";
    private static final String COLUMN_PROMOTION_END_DAY = "PROMOTION_End_Day";
    private static final String F_PROMOTION_COLUMN_ID_TYPE_PRODUCT = "ID_TypeProduct_PROMOTION";
    private static final String F_PROMOTION_COLUMN_ID_PRODUCT = "ID_Product_PROMOTION";
    private static final String F_PROMOTION_COLUMN_ID_USER = "ID_User_Promotion";
    //Promotion

    //ImportProduct
    private static final String TABLE_IMPORT_PRODUCT = "IMPORT_PRODUCT";
    private static final String COLUMN_ID_IMPORT_PRODUCT = "ID_Import_Product";
    private static final String COLUMN_IMPORT_PRODUCT_OLD_QUALITY = "Import_Product_Old_Quality";
    private static final String COLUMN_IMPORT_PRODUCT_NEW_QUALITY = "Import_Product_New_Quality";
    private static final String COLUMN_IMPORT_PRODUCT_TOTAL_PRICE = "Import_Product_Total_Price";
    private static final String COLUMN_IMPORT_PRODUCT_CREATE_DAY = "Import_Product_Create_Day";
    private static final String COLUMN_IMPORT_PRODUCT_CREATE_TIME = "Import_Product_Create_Time";
    private static final String F_IMPORT_PRODUCT_COLUMN_ID_TYPE_PRODUCT = "ID_TypeProduct_Import_Product";
    private static final String F_IMPORT_PRODUCT_COLUMN_ID_PRODUCT = "ID_Product_Import_Product";
    private static final String F_IMPORT_PRODUCT_COLUMN_ID_USER = "ID_User_Import_Product";
    //ImportProduct



    public SqlDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    //Login_Signup
    private String Create_Table_Users =  "CREATE TABLE IF NOT EXISTS " + TABLE_USERS +
                                        "(" + COLUMN_ID_USERS + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                        COLUMN_USERNAME_USERS + " TEXT UNIQUE, " +
                                        COLUMN_PASSWORD_USERS + " TEXT, " +
                                        COLUMN_NAME_USERS + " TEXT, " +
                                        COLUMN_EMAIL_USERS + " TEXT UNIQUE," +
                                        COLUMN_PHONE_USERS + " TEXT UNIQUE, " +
                                        COLUMN_GENDER_USERS + " TEXT, " +
                                        COLUMN_AGE_USERS + " TEXT, " +
                                        COLUMN_IMAGE_USERS + " BLOB, " +
                                        COLUMN_CREATE_DAY_USERS + " TEXT);";
    //Login_Signup


    //TypeProduct
    private String Create_Table_Type_Product =  "CREATE TABLE IF NOT EXISTS " + TABLE_TYPE_PRODUCT +
            "(" + COLUMN_ID_TYPE_PRODUCT + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_TYPE_PRODUCT_NAME + " TEXT , " +
            COLUMN_TYPE_PRODUCT_DESCRIPTION + " TEXT, " +
            F_TYPE_PRODUCT_COLUMN_ID_USER + " INTEGER, " +
            "FOREIGN KEY ("+F_TYPE_PRODUCT_COLUMN_ID_USER+") REFERENCES "+TABLE_USERS+"("+COLUMN_ID_USERS+"));";
    //TypeProduct


    //Product
    private String Create_Table_Product =  "CREATE TABLE IF NOT EXISTS " + TABLE_PRODUCT +
            "(" + COLUMN_ID_PRODUCT + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_PRODUCT_NAME + " TEXT , " +
            COLUMN_PRODUCT_QUALITY + " TEXT, " +
            COLUMN_PRODUCT_UNIT + " TEXT, " +
            COLUMN_PRODUCT_PRICE + " REAL," +
            COLUMN_PRODUCT_IMAGE + " BLOB, " +
            F_PRODUCT_COLUMN_ID_TYPE_PRODUCT + " INTEGER," +
            F_PRODUCT_COLUMN_ID_USER + " INTEGER," +
            "FOREIGN KEY ("+F_PRODUCT_COLUMN_ID_TYPE_PRODUCT+") REFERENCES "+TABLE_TYPE_PRODUCT+"("+COLUMN_ID_TYPE_PRODUCT+"), "+
            "FOREIGN KEY ("+F_PRODUCT_COLUMN_ID_USER+") REFERENCES "+TABLE_USERS+"("+COLUMN_ID_USERS+"));";
    //Product


    //Bill
    private String Create_Table_Bill=  "CREATE TABLE IF NOT EXISTS " + TABLE_BILL +
            "(" + COLUMN_ID_BILL + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_BILL_QUALITY + " TEXT, " +
            COLUMN_BILL_TOTAL_PRICE + " REAL, " +
            COLUMN_BILL_CREATE_DAY + " TEXT," +
            COLUMN_BILL_CREATE_TIME + " TEXT," +
            F_BILL_COLUMN_ID_TYPE_PRODUCT + " INTEGER, " +
            F_BILL_COLUMN_ID_PRODUCT + " INTEGER, " +
            F_BILL_COLUMN_ID_USER + " INTEGER, " +
            "FOREIGN KEY ("+F_BILL_COLUMN_ID_TYPE_PRODUCT+") REFERENCES "+TABLE_TYPE_PRODUCT+"("+COLUMN_ID_TYPE_PRODUCT+") ," +
            "FOREIGN KEY ("+F_BILL_COLUMN_ID_PRODUCT+") REFERENCES "+TABLE_PRODUCT+"("+COLUMN_ID_PRODUCT+"), " +
            "FOREIGN KEY ("+F_BILL_COLUMN_ID_USER+") REFERENCES "+TABLE_USERS+"("+COLUMN_ID_USERS+"));";
    //Bill


    //Promotion
    private String Create_Table_Promotion=  "CREATE TABLE IF NOT EXISTS " + TABLE_PROMOTION +
            "(" + COLUMN_ID_PROMOTION + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_PROMOTION_PERCENT + " TEXT, " +
            COLUMN_PROMOTION_PRICE_AFTER + " REAL, " +
            COLUMN_PROMOTION_START_DAY + " TEXT," +
            COLUMN_PROMOTION_END_DAY + " TEXT," +
            F_PROMOTION_COLUMN_ID_TYPE_PRODUCT + " INTEGER, " +
            F_PROMOTION_COLUMN_ID_PRODUCT + " INTEGER, " +
            F_PROMOTION_COLUMN_ID_USER + " INTEGER, " +
            "FOREIGN KEY ("+F_PROMOTION_COLUMN_ID_TYPE_PRODUCT+") REFERENCES "+TABLE_TYPE_PRODUCT+"("+COLUMN_ID_TYPE_PRODUCT+")," +
            "FOREIGN KEY ("+F_PROMOTION_COLUMN_ID_PRODUCT+") REFERENCES "+TABLE_PRODUCT+"("+COLUMN_ID_PRODUCT+"), " +
            "FOREIGN KEY ("+F_PROMOTION_COLUMN_ID_USER+") REFERENCES "+TABLE_USERS+"("+COLUMN_ID_USERS+"));";
    //Promotion


    //ImportProduct
    private String Create_Table_Import_Product=  "CREATE TABLE IF NOT EXISTS " + TABLE_IMPORT_PRODUCT +
            "(" + COLUMN_ID_IMPORT_PRODUCT + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_IMPORT_PRODUCT_OLD_QUALITY + " TEXT, " +
            COLUMN_IMPORT_PRODUCT_NEW_QUALITY + " TEXT, " +
            COLUMN_IMPORT_PRODUCT_TOTAL_PRICE + " REAL, " +
            COLUMN_IMPORT_PRODUCT_CREATE_DAY + " TEXT," +
            COLUMN_IMPORT_PRODUCT_CREATE_TIME + " TEXT," +
            F_IMPORT_PRODUCT_COLUMN_ID_TYPE_PRODUCT + " INTEGER, " +
            F_IMPORT_PRODUCT_COLUMN_ID_PRODUCT + " INTEGER, " +
            F_IMPORT_PRODUCT_COLUMN_ID_USER + " INTEGER, " +
            "FOREIGN KEY ("+F_IMPORT_PRODUCT_COLUMN_ID_TYPE_PRODUCT+") REFERENCES "+TABLE_TYPE_PRODUCT+"("+COLUMN_ID_TYPE_PRODUCT+") ," +
            "FOREIGN KEY ("+F_IMPORT_PRODUCT_COLUMN_ID_PRODUCT+") REFERENCES "+TABLE_PRODUCT+"("+COLUMN_ID_PRODUCT+"), " +
            "FOREIGN KEY ("+F_IMPORT_PRODUCT_COLUMN_ID_USER+") REFERENCES "+TABLE_USERS+"("+COLUMN_ID_USERS+"));";
    //ImportProduct


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //Login_Signup
        sqLiteDatabase.execSQL(Create_Table_Users);
        //Login_Signup


        //TypeProduct
        sqLiteDatabase.execSQL(Create_Table_Type_Product);
        //TypeProduct


        //Product
        sqLiteDatabase.execSQL(Create_Table_Product);
        //Product


        //Bill
        sqLiteDatabase.execSQL(Create_Table_Bill);
        //Bill


        //Promotion
        sqLiteDatabase.execSQL(Create_Table_Promotion);
        //Promotion


        //ImportProduct
        sqLiteDatabase.execSQL(Create_Table_Import_Product);
        //ImportProduct
    }


    //Login_Signup
    private String Update_Table_Users = "DROP TABLE IF EXISTS " + TABLE_USERS;
    //Login_Signup


    //TypeProduct
    private String Update_Table_Type_Product = "DROP TABLE IF EXISTS " + TABLE_TYPE_PRODUCT;
    //TypeProduct


    //Product
    private String Update_Table_Product = "DROP TABLE IF EXISTS " + TABLE_PRODUCT;
    //Product


    //Bill
    private String Update_Table_Bill = "DROP TABLE IF EXISTS " + TABLE_BILL;
    //Bill


    //Promotion
    private String Update_Table_Promotion = "DROP TABLE IF EXISTS " + TABLE_PROMOTION;
    //Promotion


    //ImportProduct
    private String Update_Table_Import_Product = "DROP TABLE IF EXISTS " + TABLE_IMPORT_PRODUCT;
    //ImportProduct


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //Users
        sqLiteDatabase.execSQL(Update_Table_Users);
        //Users


        //TypeProduct
        sqLiteDatabase.execSQL(Update_Table_Type_Product);
        //TypeProduct


        //Product
        sqLiteDatabase.execSQL(Update_Table_Product);
        //Product


        //Bill
        sqLiteDatabase.execSQL(Update_Table_Bill);
        //Bill


        //Promotion
        sqLiteDatabase.execSQL(Update_Table_Promotion);
        //Promotion


        //ImportProduct
        sqLiteDatabase.execSQL(Update_Table_Import_Product);
        //ImportProduct


        onCreate(sqLiteDatabase);
    }

    //Login_Signup
    public boolean insertData_Users(String username, String password, String name, String email, String phone, String gender, String age, String createDay){
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(COLUMN_USERNAME_USERS,username);
            contentValues.put(COLUMN_PASSWORD_USERS,password);
            contentValues.put(COLUMN_NAME_USERS,name);
            contentValues.put(COLUMN_EMAIL_USERS,email);
            contentValues.put(COLUMN_PHONE_USERS,phone);
            contentValues.put(COLUMN_GENDER_USERS,gender);
            contentValues.put(COLUMN_AGE_USERS,age);
            contentValues.put(COLUMN_CREATE_DAY_USERS,createDay);

        long result = db.insert(TABLE_USERS,null, contentValues);

        if(result == -1){
            return false;
        }else{
            return true;
        }
    }

    public boolean checkUsername_Users(String username){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from "+ TABLE_USERS +" where "+ COLUMN_USERNAME_USERS +" = ?", new String[] {username});

        if (cursor.getCount()>0){
            return true;
        }else{
            return false;
        }
    }

    public boolean checkEmail_Users(String email){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from "+ TABLE_USERS +" where "+ COLUMN_EMAIL_USERS +" = ?", new String[] {email});

        if (cursor.getCount()>0){
            return true;
        }else{
            return false;
        }
    }

    public boolean checkPhone_Users(String phone){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from "+ TABLE_USERS +" where "+ COLUMN_PHONE_USERS +" = ?", new String[] {phone});


        if (cursor.getCount()>0){
            return true;
        }else{
            return false;
        }
    }

    public boolean checkUsernamePassword_Users(String username, String password){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from "+ TABLE_USERS +" where "+ COLUMN_USERNAME_USERS +" = ? AND "+ COLUMN_PASSWORD_USERS +" = ?", new String[] {username,password});

        if (cursor.getCount()>0){
            return true;
        }else{
            return false;
        }
    }

    public boolean checkEmailPassword_Users(String email, String password){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from "+ TABLE_USERS +" where "+ COLUMN_EMAIL_USERS +" = ? AND "+ COLUMN_PASSWORD_USERS +" = ?", new String[] {email,password});

        if (cursor.getCount()>0){
            return true;
        }else{
            return false;
        }
    }

    public boolean checkPhonePassword_Users(String phone, String password){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from "+ TABLE_USERS +" where "+ COLUMN_PHONE_USERS +" = ? AND "+ COLUMN_PASSWORD_USERS +" = ?", new String[] {phone,password});

        if (cursor.getCount()>0){
            return true;
        }else{
            return false;
        }
    }

    public boolean checkUsernameExist_Users(String username){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from "+ TABLE_USERS +" where "+ COLUMN_USERNAME_USERS +" = ?", new String[] {username});

        if (cursor.getCount()>0){
            return true;
        }else{
            return false;
        }
    }

    public boolean checkEmailExist_Users(String email){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from "+ TABLE_USERS +" where "+ COLUMN_EMAIL_USERS +" = ?", new String[] {email});

        if (cursor.getCount()>0){
            return true;
        }else{
            return false;
        }
    }

    public boolean checkPhoneExist_Users(String phone){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from "+ TABLE_USERS +" where "+ COLUMN_PHONE_USERS +" = ?", new String[] {phone});

        if (cursor.getCount()>0){
            return true;
        }else{
            return false;
        }
    }

    public boolean changeForgetPassword_Users(String login_signup_email, String login_signup_password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_PASSWORD_USERS,login_signup_password);
        long result = db.update(TABLE_USERS,contentValues,COLUMN_EMAIL_USERS + " = ?", new String[] {login_signup_email});

        if(result == -1){
            return false;
        }else{
            return true;
        }
    }

    public Cursor getId_User(String user_name, String email, String phone){

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery("Select " + COLUMN_ID_USERS + " From " + TABLE_USERS + " Where " + COLUMN_USERNAME_USERS + "= ? OR " + COLUMN_EMAIL_USERS + "= ? OR " + COLUMN_PHONE_USERS + "= ?",new String[] {user_name,email,phone});
        }
        return cursor;
    }

    public Cursor readDataForMenu_User(Integer id_user){

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery("Select " + COLUMN_NAME_USERS + " From " + TABLE_USERS + " Where " + COLUMN_ID_USERS + "= ?",new String[] {String.valueOf(id_user)});
        }
        return cursor;
    }

    public Cursor readAllData_User(Integer id_user){

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery("Select * From " + TABLE_USERS + " Where " + COLUMN_ID_USERS + "= ?",new String[] {String.valueOf(id_user)});
        }
        return cursor;

    }

    public boolean checkOldPassword_Users(Integer id_user, String password_user){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = null;
        cursor = db.rawQuery("Select " + COLUMN_PASSWORD_USERS + " From " + TABLE_USERS + " Where " + COLUMN_ID_USERS + "= ? AND "+ COLUMN_PASSWORD_USERS + "= ?",new String[] {String.valueOf(id_user),password_user});

        if(cursor.getCount() > 0){
            return true;
        }else{
            return false;
        }
    }

    public boolean updatePassword_Users(Integer id_user, String password_user){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_PASSWORD_USERS,password_user);
        long resultUpdate = db.update(TABLE_USERS, contentValues, COLUMN_ID_USERS + "= ?", new String[] {String.valueOf(id_user)});

        if(resultUpdate == -1){
            return false;
        }else{
            return true;
        }
    }

    public boolean updateData_Users(Integer id_user, String username, String name, String email, String phone, String gender,  String age, GetImageUserClass imageUser){

        SQLiteDatabase db = this.getWritableDatabase();

        byteArrayOutputStreamUser = new ByteArrayOutputStream();
        Bitmap imageStoreBitMap = imageUser.getImageUser();
        imageStoreBitMap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStreamUser);

        imageUserByte = byteArrayOutputStreamUser.toByteArray();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_USERNAME_USERS,username);
        contentValues.put(COLUMN_NAME_USERS,name);
        contentValues.put(COLUMN_EMAIL_USERS,email);
        contentValues.put(COLUMN_PHONE_USERS,phone);
        contentValues.put(COLUMN_GENDER_USERS,gender);
        contentValues.put(COLUMN_AGE_USERS,age);
        contentValues.put(COLUMN_IMAGE_USERS,imageUserByte);

        long result = db.update(TABLE_USERS, contentValues, COLUMN_ID_USERS + "= ?", new String[] {String.valueOf(id_user)});

        if(result == -1){
            return false;
        }else{
            return true;
        }
    }

    public Cursor readImageExists_User(Integer id_user){

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery("Select " + COLUMN_IMAGE_USERS +" From " + TABLE_USERS + " Where " + COLUMN_ID_USERS + "= ?",new String[] {String.valueOf(id_user)});
        }
        return cursor;

    }

    public Boolean deleteAccount_User(Integer id_user){

        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_USERS,COLUMN_ID_USERS + "= ?", new String[] {String.valueOf(id_user)});
        if(result == -1){
            return false;
        }else{
            return true;
        }

    }

    public Cursor setRememberUserName_User(Integer id_user){

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery("Select " + COLUMN_USERNAME_USERS + " From " + TABLE_USERS + " Where " + COLUMN_ID_USERS + "= ?", new String[] {String.valueOf(id_user)});
        }
        return cursor;
    }
    //Login_Signup


    //TypeProduct
    public boolean insertData_TypeProduct(String type_product_name, String type_product_description, Integer id_user){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_TYPE_PRODUCT_NAME,type_product_name);
        contentValues.put(COLUMN_TYPE_PRODUCT_DESCRIPTION,type_product_description);
        contentValues.put(F_TYPE_PRODUCT_COLUMN_ID_USER,id_user);

        long result = db.insert(TABLE_TYPE_PRODUCT,null, contentValues);

        if(result == -1){
            return false;
        }else{
            return true;
        }
    }

    public boolean checkNameTypeProduct_TypeProduct(String type_product_name, Integer id_user){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from "+ TABLE_TYPE_PRODUCT +" Where " + COLUMN_TYPE_PRODUCT_NAME + " = ?" + " And " +
                F_TYPE_PRODUCT_COLUMN_ID_USER + "=" + id_user,new String[] {type_product_name});

        if (cursor.getCount()>0){
            return true;
        }else{
            return false;
        }
    }

    public Cursor readData_TypeProduct(Integer id_user){
        String query= "Select * from "+ TABLE_TYPE_PRODUCT + " Where " + F_TYPE_PRODUCT_COLUMN_ID_USER + " = " + id_user;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query,null);
        }
        return cursor;
    }

    public boolean deleteData_TypeProduct(Integer type_product_id){

        SQLiteDatabase db = this.getWritableDatabase();
        long resultDelete = db.delete(TABLE_TYPE_PRODUCT, COLUMN_ID_TYPE_PRODUCT + "= ?", new String[]{String.valueOf(type_product_id)});
        if(resultDelete == -1){
            return false;
        }else{
            return true;
        }

    }

    public Cursor readNameTypeProductExist_TypeProduct(Integer type_product_id){

        String query= "Select " + COLUMN_TYPE_PRODUCT_NAME + " from "+ TABLE_TYPE_PRODUCT + " Where " + COLUMN_ID_TYPE_PRODUCT + " = " + type_product_id;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query,null);
        }
        return cursor;

    }

    public Boolean updateData_TypeProduct(String name_type_product, String desc_type_product, Integer type_product_id){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_TYPE_PRODUCT_NAME,name_type_product);
        contentValues.put(COLUMN_TYPE_PRODUCT_DESCRIPTION,desc_type_product);

        long resultUpdateDate = db.update(TABLE_TYPE_PRODUCT,contentValues,COLUMN_ID_TYPE_PRODUCT + "= ?",new String[] {String.valueOf(type_product_id)});

        if(resultUpdateDate == -1){
            return false;
        }else{
            return true;
        }
    }

    public Cursor readDataUpdate_TypeProduct(Integer type_product_id){
        String query= "Select * from "+ TABLE_TYPE_PRODUCT + " Where " + COLUMN_ID_TYPE_PRODUCT + " = " + type_product_id;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query,null);
        }
        return cursor;
    }
    //TypeProduct


    //Product
    public Cursor readTypeProduct_Product(Integer id_user){
        String query= "Select * from "+ TABLE_TYPE_PRODUCT + " Where " + F_TYPE_PRODUCT_COLUMN_ID_USER + "=" + id_user;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query,null);
        }
        return cursor;
    }

    public boolean insertData_Product(String product_name, String product_quality, String product_unit, String product_price, GetImageProductClass imageProduct, Integer type_product_id, Integer id_user){

        SQLiteDatabase db = this.getWritableDatabase();
        Bitmap imageStoreBitMap = imageProduct.getImage();

        byteArrayOutputStreamProduct = new ByteArrayOutputStream();
        imageStoreBitMap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStreamProduct);

        imageProductByte = byteArrayOutputStreamProduct.toByteArray();

        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_PRODUCT_NAME,product_name);
        contentValues.put(COLUMN_PRODUCT_QUALITY,product_quality);
        contentValues.put(COLUMN_PRODUCT_UNIT,product_unit);
        contentValues.put(COLUMN_PRODUCT_PRICE,product_price);
        contentValues.put(COLUMN_PRODUCT_IMAGE,imageProductByte);
        contentValues.put(F_PRODUCT_COLUMN_ID_TYPE_PRODUCT,type_product_id);
        contentValues.put(F_PRODUCT_COLUMN_ID_USER,id_user);

        long result = db.insert(TABLE_PRODUCT,null, contentValues);

        if(result == -1){
            return false;
        }else{
            return true;
        }
    }

    public Cursor getTypeProductID_Product(String type_product_name, Integer id_user){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery("Select * from "+ TABLE_TYPE_PRODUCT +" Where "+ COLUMN_TYPE_PRODUCT_NAME +" = ?" +
                    " And " + F_TYPE_PRODUCT_COLUMN_ID_USER + "=" + id_user,new String[] {type_product_name});
        }
        return cursor;
    }

    public boolean checkNameProduct_Product(String product_name, Integer id_user){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("select * from "+ TABLE_PRODUCT +" where "+ COLUMN_PRODUCT_NAME +" = ?" +
                " And " + F_PRODUCT_COLUMN_ID_USER + "=" + id_user, new String[] {product_name});

        if (cursor.getCount()>0){
            return true;
        }else{
            return false;
        }
    }

    public Cursor readData_Product(Integer id_user){
        String query = "Select "+ COLUMN_ID_PRODUCT +","+
                COLUMN_TYPE_PRODUCT_NAME +","+
                COLUMN_PRODUCT_NAME +","+
                COLUMN_PRODUCT_QUALITY +","+
                COLUMN_PRODUCT_UNIT +"," +
                COLUMN_PRODUCT_PRICE +","+
                COLUMN_PRODUCT_IMAGE +
                " From "+ TABLE_PRODUCT+ ","+ TABLE_TYPE_PRODUCT +
                " Where "+ TABLE_PRODUCT +"."+ F_PRODUCT_COLUMN_ID_TYPE_PRODUCT +"="+ TABLE_TYPE_PRODUCT +"."+ COLUMN_ID_TYPE_PRODUCT +
                " And " + F_PRODUCT_COLUMN_ID_USER + "=" + id_user +
                " ORDER BY " + COLUMN_ID_PRODUCT + " ASC ";

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
           cursor = db.rawQuery(query ,null);
        }

        return cursor;
    }

    public Cursor ReadAllData_Product(Integer product_id){

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery("Select "+ COLUMN_ID_PRODUCT + "," +
                    COLUMN_TYPE_PRODUCT_NAME +","+
                    COLUMN_PRODUCT_NAME +","+
                    COLUMN_PRODUCT_QUALITY +","+
                    COLUMN_PRODUCT_UNIT +"," +
                    COLUMN_PRODUCT_PRICE +","+
                    COLUMN_PRODUCT_IMAGE +
                    " From "+ TABLE_PRODUCT+ ","+ TABLE_TYPE_PRODUCT +
                    " Where "+ TABLE_PRODUCT +"."+ F_PRODUCT_COLUMN_ID_TYPE_PRODUCT +"="+ TABLE_TYPE_PRODUCT +"."+ COLUMN_ID_TYPE_PRODUCT +
                    " AND " + COLUMN_ID_PRODUCT + " = " +product_id,null);
        }

        return cursor;
    }

    public boolean deleteData_Product(Integer product_id){

        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_PRODUCT,COLUMN_ID_PRODUCT + "=" + product_id,null);
        if(result == -1){
            return false;
        }else{
            return true;
        }
    }

    public boolean updateData_Product(Integer product_id, String product_name, String product_quality, String product_unit, String product_price, GetImageProductClass imageProduct, Integer type_product_id) {

        SQLiteDatabase db = this.getWritableDatabase();
        Bitmap imageStoreBitMap = imageProduct.getImage();

        byteArrayOutputStreamProduct = new ByteArrayOutputStream();
        imageStoreBitMap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStreamProduct);

        imageProductByte = byteArrayOutputStreamProduct.toByteArray();

        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_PRODUCT_NAME, product_name);
        contentValues.put(COLUMN_PRODUCT_QUALITY, product_quality);
        contentValues.put(COLUMN_PRODUCT_UNIT, product_unit);
        contentValues.put(COLUMN_PRODUCT_PRICE, product_price);
        contentValues.put(COLUMN_PRODUCT_IMAGE, imageProductByte);
        contentValues.put(F_PRODUCT_COLUMN_ID_TYPE_PRODUCT, type_product_id);
        long result = db.update(TABLE_PRODUCT, contentValues, COLUMN_ID_PRODUCT + "=" + product_id, null);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }
    public Cursor checkImageProductExits_Product(Integer product_id){

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if(db!=null){

            cursor = db.rawQuery("Select " + COLUMN_PRODUCT_IMAGE + " From " + TABLE_PRODUCT + " Where " + COLUMN_ID_PRODUCT + "=" + product_id, null);

        }
        return cursor;

    }

    public Cursor readNameProductExist_Product(Integer product_id){

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if(db!=null){

            cursor = db.rawQuery("Select " + COLUMN_PRODUCT_NAME + " From " + TABLE_PRODUCT + " Where " + COLUMN_ID_PRODUCT + "=" + product_id, null);

        }
        return cursor;

    }
    //Product


    //Bill
    public Cursor getIDTypeProduct_Bill(String type_product_name, Integer id_user){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery("Select "+ COLUMN_ID_TYPE_PRODUCT +" from "+ TABLE_TYPE_PRODUCT +" Where " + COLUMN_TYPE_PRODUCT_NAME + "= ? " + " And " + F_TYPE_PRODUCT_COLUMN_ID_USER + "=" + id_user,new String[] {type_product_name});
        }
        return cursor;
    }

    public Cursor getIDProduct_Bill(String product_name, Integer id_user){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery("Select "+ COLUMN_ID_PRODUCT + "," + COLUMN_PRODUCT_PRICE + " from "+ TABLE_PRODUCT +" Where " + COLUMN_PRODUCT_NAME + "= ?"+ " And " + F_PRODUCT_COLUMN_ID_USER + "=" + id_user,new String[] {product_name});
        }
        return cursor;
    }

    public Cursor readTypeProduct_Bill(Integer id_user){
        String query= "Select * from "+ TABLE_TYPE_PRODUCT + " Where " + F_TYPE_PRODUCT_COLUMN_ID_USER + "=" + id_user;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query,null);
        }
        return cursor;
    }

    public Cursor readNameProduct_Bill(Integer type_product_id){
        String query= "Select "+ COLUMN_PRODUCT_NAME +"," + COLUMN_PRODUCT_PRICE +" from "+ TABLE_PRODUCT +" Where " + F_PRODUCT_COLUMN_ID_TYPE_PRODUCT + "="+ type_product_id;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query,null);
        }
        return cursor;
    }

    public Cursor readPriceProduct_Bill(String product_name, Integer id_user){

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery("Select " + COLUMN_PRODUCT_PRICE +" from "+ TABLE_PRODUCT +" Where " + COLUMN_PRODUCT_NAME + "= ?" + " And " + F_PRODUCT_COLUMN_ID_USER + "=" + id_user, new String[] {product_name});
        }
        return cursor;
    }

    public boolean insertData_Bill(String product_quality, String total_price, String bill_create_Day, String bill_create_Time, Integer type_product_id, Integer product_id, Integer id_user){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_BILL_QUALITY,product_quality);
        contentValues.put(COLUMN_BILL_TOTAL_PRICE,total_price);
        contentValues.put(COLUMN_BILL_CREATE_DAY,bill_create_Day);
        contentValues.put(COLUMN_BILL_CREATE_TIME,bill_create_Time);
        contentValues.put(F_BILL_COLUMN_ID_TYPE_PRODUCT,type_product_id);
        contentValues.put(F_BILL_COLUMN_ID_PRODUCT,product_id);
        contentValues.put(F_BILL_COLUMN_ID_USER,id_user);

        long result = db.insert(TABLE_BILL,null, contentValues);

        if(result == -1){
            return false;
        }else{
            return true;
        }
    }

    public Cursor readData_Bill(Integer id_user){
        String query = "Select "+ COLUMN_ID_BILL + "," +
                COLUMN_PRODUCT_NAME +","+
                COLUMN_BILL_QUALITY +","+
                COLUMN_BILL_CREATE_DAY +","+
                COLUMN_PRODUCT_IMAGE +","+
                COLUMN_BILL_CREATE_TIME +
                " From "+ TABLE_BILL + ","+ TABLE_PRODUCT +
                " Where "+ TABLE_BILL +"."+ F_BILL_COLUMN_ID_PRODUCT +"="+ TABLE_PRODUCT +"."+ COLUMN_ID_PRODUCT +
                " And " + F_BILL_COLUMN_ID_USER + "=" + id_user +
                " ORDER BY " + COLUMN_ID_BILL + " ASC ";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if (db != null){
            cursor = db.rawQuery(query,null);
        }
        return cursor;
    }

    public Cursor readAllData_Bill(Integer bill_id){
        String query = "Select "+ COLUMN_ID_BILL + "," +
                COLUMN_TYPE_PRODUCT_NAME +","+
                COLUMN_PRODUCT_NAME +","+
                COLUMN_PRODUCT_PRICE +","+
                COLUMN_BILL_QUALITY +","+
                COLUMN_BILL_TOTAL_PRICE +","+
                COLUMN_BILL_CREATE_DAY +","+
                COLUMN_BILL_CREATE_TIME +","+
                COLUMN_PRODUCT_IMAGE +
                " From "+ TABLE_BILL + ","+ TABLE_PRODUCT + "," + TABLE_TYPE_PRODUCT +
                " Where "+ TABLE_BILL +"."+ F_BILL_COLUMN_ID_PRODUCT +"="+ TABLE_PRODUCT +"."+ COLUMN_ID_PRODUCT +
                " And " + TABLE_BILL +"."+ F_BILL_COLUMN_ID_TYPE_PRODUCT +"="+ TABLE_TYPE_PRODUCT +"."+ COLUMN_ID_TYPE_PRODUCT +
                " And " + COLUMN_ID_BILL + " = " +bill_id;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if (db != null){
            cursor = db.rawQuery(query,null);
        }
        return cursor;
    }

    public boolean deleteData_Bill(Integer bill_id){

        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_BILL,COLUMN_ID_BILL + "= ?", new String[] {String.valueOf(bill_id)});
        if(result == -1){
            return false;
        }else{
            return true;
        }
    }

    public boolean updateData_Bill(Integer id_bill,String product_quality, String total_price, String bill_create_Day, String bill_create_Time, Integer type_product_id, Integer product_id) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_BILL_QUALITY, product_quality);
        contentValues.put(COLUMN_BILL_TOTAL_PRICE, total_price);
        contentValues.put(COLUMN_BILL_CREATE_DAY, bill_create_Day);
        contentValues.put(COLUMN_BILL_CREATE_TIME, bill_create_Time);
        contentValues.put(F_BILL_COLUMN_ID_TYPE_PRODUCT, type_product_id);
        contentValues.put(F_BILL_COLUMN_ID_PRODUCT, product_id);

        long result = db.update(TABLE_BILL, contentValues, COLUMN_ID_BILL + "= ?", new String[]{String.valueOf(id_bill)});

        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Cursor getQualityProduct_Bill(Integer product_id){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery("Select "+ COLUMN_PRODUCT_QUALITY +" from "+ TABLE_PRODUCT +" Where " + COLUMN_ID_PRODUCT + "= ?",new String[] {String.valueOf(product_id)});
        }
        return cursor;
    }

    public Cursor getQualityProductBill_Bill(Integer bill_id){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery("Select "+ COLUMN_BILL_QUALITY +" from "+ TABLE_BILL +" Where " + COLUMN_ID_BILL + "= ?",new String[] {String.valueOf(bill_id)});
        }
        return cursor;
    }

    public Cursor readImageProduct_Bill(String product_name, Integer id_user){

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery("Select " + COLUMN_PRODUCT_IMAGE +" from "+ TABLE_PRODUCT +" Where " + COLUMN_PRODUCT_NAME + "= ?" + " And " + F_PRODUCT_COLUMN_ID_USER + "=" +id_user, new String[] {product_name});
        }
        return cursor;
    }

    public boolean updateNewQualityProduct_Bill(String product_quality, Integer id_product) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_PRODUCT_QUALITY, product_quality);

        long result = db.update(TABLE_PRODUCT, contentValues, COLUMN_ID_PRODUCT + "= ?", new String[]{String.valueOf(id_product)});

        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean checkProductPromotion_Bill(Integer id_product, String CDay){

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        cursor = db.rawQuery("Select * from "+ TABLE_PROMOTION +" Where " + F_PROMOTION_COLUMN_ID_PRODUCT + "="+ id_product +
                " And " + COLUMN_PROMOTION_START_DAY + "=?",new String[]{CDay});
        if(cursor.getCount() > 0){
            return true;
        }else{
            return false;
        }
    }

    public Cursor getProductPromotion_Bill(Integer id_product){

        String query= "Select "+ COLUMN_PROMOTION_PRICE_AFTER +" from "+ TABLE_PROMOTION +" Where " + F_PROMOTION_COLUMN_ID_PRODUCT + "="+ id_product;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query,null);
        }
        return cursor;
    }
    //Bill


    //Promotion
    public Cursor getIDTypeProduct_Promotion(String type_product_name, Integer id_user){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery("Select "+ COLUMN_ID_TYPE_PRODUCT +" from "+ TABLE_TYPE_PRODUCT +" Where " + COLUMN_TYPE_PRODUCT_NAME + "= ?" + " And " + F_TYPE_PRODUCT_COLUMN_ID_USER + "=" + id_user,new String[] {type_product_name});
        }
        return cursor;
    }

    public Cursor getIDProduct_Promotion(String product_name, Integer id_user){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery("Select "+ COLUMN_ID_PRODUCT +" from "+ TABLE_PRODUCT +" Where " + COLUMN_PRODUCT_NAME + "= ?" + " And " +  F_PRODUCT_COLUMN_ID_USER + "=" + id_user,new String[] {product_name});
        }
        return cursor;
    }

    public Cursor readTypeProduct_Promotion(Integer id_user){
        String query= "Select * from "+ TABLE_TYPE_PRODUCT + " Where " + F_TYPE_PRODUCT_COLUMN_ID_USER + "=" + id_user;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query,null);
        }
        return cursor;
    }

    public Cursor readNameProduct_Promotion(Integer type_product_id){
        String query= "Select "+ COLUMN_PRODUCT_NAME +"," + COLUMN_PRODUCT_PRICE +" from "+ TABLE_PRODUCT +" Where " + F_PRODUCT_COLUMN_ID_TYPE_PRODUCT + "="+ type_product_id;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query,null);
        }
        return cursor;
    }

    public Cursor readPriceProduct_Promotion(String product_name, Integer id_user){

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery("Select " + COLUMN_PRODUCT_PRICE +" from "+ TABLE_PRODUCT +" Where " + COLUMN_PRODUCT_NAME + "= ?" + " And " + F_PRODUCT_COLUMN_ID_USER + "=" + id_user , new String[] {product_name});
        }
        return cursor;
    }

    public boolean insertData_Promotion(String promotion_percent, String promotion_price_after, String promotion_start_day, String promotion_end_day, Integer type_product_id, Integer product_id, Integer id_user){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_PROMOTION_PERCENT,promotion_percent);
        contentValues.put(COLUMN_PROMOTION_PRICE_AFTER,promotion_price_after);
        contentValues.put(COLUMN_PROMOTION_START_DAY,promotion_start_day);
        contentValues.put(COLUMN_PROMOTION_END_DAY,promotion_end_day);
        contentValues.put(F_PROMOTION_COLUMN_ID_TYPE_PRODUCT,type_product_id);
        contentValues.put(F_PROMOTION_COLUMN_ID_PRODUCT,product_id);
        contentValues.put(F_PROMOTION_COLUMN_ID_USER,id_user);

        long result = db.insert(TABLE_PROMOTION,null, contentValues);

        if(result == -1){
            return false;
        }else{
            return true;
        }
    }

    public Cursor readData_Promotion(Integer id_user){
        String query = "Select "+ COLUMN_ID_PROMOTION + "," +
                COLUMN_PRODUCT_NAME +","+
                COLUMN_PROMOTION_PERCENT +","+
                COLUMN_PROMOTION_START_DAY +","+
                COLUMN_PROMOTION_END_DAY + "," +
                COLUMN_PRODUCT_IMAGE +
                " From "+ TABLE_PROMOTION + ","+ TABLE_PRODUCT +
                " Where "+ TABLE_PROMOTION +"."+ F_PROMOTION_COLUMN_ID_PRODUCT +"="+ TABLE_PRODUCT +"."+ COLUMN_ID_PRODUCT +
                " And " + F_PROMOTION_COLUMN_ID_USER + "=" + id_user +
                " ORDER BY " + COLUMN_ID_PROMOTION + " ASC ";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if (db != null){
            cursor = db.rawQuery(query,null);
        }
        return cursor;
    }

    public Cursor readAllData_Promotion(Integer promotion_id){
        String query = "Select "+ COLUMN_ID_PROMOTION + "," +
                COLUMN_TYPE_PRODUCT_NAME +","+
                COLUMN_PRODUCT_NAME +","+
                COLUMN_PRODUCT_PRICE + ","+
                COLUMN_PROMOTION_PERCENT +","+
                COLUMN_PROMOTION_PRICE_AFTER +","+
                COLUMN_PROMOTION_START_DAY +","+
                COLUMN_PROMOTION_END_DAY +","+
                COLUMN_PRODUCT_IMAGE +
                " From "+ TABLE_PROMOTION + ","+ TABLE_PRODUCT + "," + TABLE_TYPE_PRODUCT +
                " Where "+ TABLE_PROMOTION +"."+ F_PROMOTION_COLUMN_ID_PRODUCT +"="+ TABLE_PRODUCT +"."+ COLUMN_ID_PRODUCT +
                " And " + TABLE_PROMOTION +"."+ F_PROMOTION_COLUMN_ID_TYPE_PRODUCT +"="+ TABLE_TYPE_PRODUCT +"."+ COLUMN_ID_TYPE_PRODUCT +
                " And " + COLUMN_ID_PROMOTION + " = " +promotion_id;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if (db != null){
            cursor = db.rawQuery(query,null);
        }
        return cursor;
    }

    public boolean checkName_Promotion(Integer product_id){

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select "+ F_PROMOTION_COLUMN_ID_PRODUCT +" from "+ TABLE_PROMOTION +" where "+ F_PROMOTION_COLUMN_ID_PRODUCT +"  = ?", new String[] {String.valueOf(product_id)});

        if (cursor.getCount()>0){
            return true;
        }else{
            return false;
        }

    }

    public boolean deleteData_Promotion(Integer promotion_id){

        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_PROMOTION,COLUMN_ID_PROMOTION + "= ?", new String[] {String.valueOf(promotion_id)});
        if(result == -1){
            return false;
        }else{
            return true;
        }
    }

    public boolean updateData_Promotion(Integer promotion_id, String promotion_percent, String promotion_price_after, String promotion_start_day, String promotion_end_day, Integer type_product_id, Integer product_id ){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_PROMOTION_PERCENT,promotion_percent);
        contentValues.put(COLUMN_PROMOTION_PRICE_AFTER,promotion_price_after);
        contentValues.put(COLUMN_PROMOTION_START_DAY,promotion_start_day);
        contentValues.put(COLUMN_PROMOTION_END_DAY,promotion_end_day);
        contentValues.put(F_PROMOTION_COLUMN_ID_TYPE_PRODUCT,type_product_id);
        contentValues.put(F_PROMOTION_COLUMN_ID_PRODUCT,product_id);

        long result = db.update(TABLE_PROMOTION, contentValues, COLUMN_ID_PROMOTION + "= ?", new String[] {String.valueOf(promotion_id)});

        if(result == -1){
            return false;
        }else{
            return true;
        }
    }

    public Cursor readImageProduct_Promotion(String product_name, Integer id_user){

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery("Select " + COLUMN_PRODUCT_IMAGE +" from "+ TABLE_PRODUCT +" Where " + COLUMN_PRODUCT_NAME + "= ?" + " And " + F_PRODUCT_COLUMN_ID_USER + "=" + id_user , new String[] {product_name});
        }
        return cursor;
    }

    public Cursor readEndDay_Promotion(Integer id_user){

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery("Select " + COLUMN_ID_PROMOTION + "," + COLUMN_PROMOTION_END_DAY + " from "+ TABLE_PROMOTION + " Where " + F_PROMOTION_COLUMN_ID_USER + "=" +id_user,null);
        }
        return cursor;
    }
    //Promotion


    //ImportProduct
    public boolean insertData_ImportProduct(String product_old_quality, String product_new_quality,  String total_price, String bill_create_Day, String bill_create_Time, Integer type_product_id, Integer product_id, Integer id_user){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_IMPORT_PRODUCT_OLD_QUALITY,product_old_quality);
        contentValues.put(COLUMN_IMPORT_PRODUCT_NEW_QUALITY,product_new_quality);
        contentValues.put(COLUMN_IMPORT_PRODUCT_TOTAL_PRICE,total_price);
        contentValues.put(COLUMN_IMPORT_PRODUCT_CREATE_DAY,bill_create_Day);
        contentValues.put(COLUMN_IMPORT_PRODUCT_CREATE_TIME,bill_create_Time);
        contentValues.put(F_IMPORT_PRODUCT_COLUMN_ID_TYPE_PRODUCT,type_product_id);
        contentValues.put(F_IMPORT_PRODUCT_COLUMN_ID_PRODUCT,product_id);
        contentValues.put(F_IMPORT_PRODUCT_COLUMN_ID_USER,id_user);

        long result = db.insert(TABLE_IMPORT_PRODUCT,null, contentValues);

        if(result == -1){
            return false;
        }else{
            return true;
        }
    }

    public boolean updateData_ImportProduct(Integer id_product,String product_quality) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_PRODUCT_QUALITY, product_quality);

        long result = db.update(TABLE_PRODUCT, contentValues, COLUMN_ID_PRODUCT + "= ?", new String[]{String.valueOf(id_product)});

        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Cursor readTypeProduct_ImportProduct(Integer id_user){
        String query= "Select * from "+ TABLE_TYPE_PRODUCT + " Where " + F_TYPE_PRODUCT_COLUMN_ID_USER + "=" +id_user;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query,null);
        }
        return cursor;
    }

    public Cursor readNameProduct_ImportProduct(Integer type_product_id){
        String query= "Select "+ COLUMN_PRODUCT_NAME +"," + COLUMN_PRODUCT_PRICE +" from "+ TABLE_PRODUCT +" Where " + F_PRODUCT_COLUMN_ID_TYPE_PRODUCT + "="+ type_product_id;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query,null);
        }
        return cursor;
    }

    public Cursor readPriceProduct_ImportProduct(String product_name, Integer id_user){

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery("Select " + COLUMN_PRODUCT_PRICE +" from "+ TABLE_PRODUCT +" Where " + COLUMN_PRODUCT_NAME + "= ?" + " And " + F_PRODUCT_COLUMN_ID_USER + "=" + id_user, new String[] {product_name});
        }
        return cursor;
    }

    public Cursor readImageProduct_ImportProduct(String product_name, Integer id_user){

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery("Select " + COLUMN_PRODUCT_IMAGE +" from "+ TABLE_PRODUCT +" Where " + COLUMN_PRODUCT_NAME + "= ?" + " And " + F_PRODUCT_COLUMN_ID_USER + "=" + id_user, new String[] {product_name});
        }
        return cursor;
    }

    public Cursor getQualityProduct_ImportProduct(String product_name, Integer id_user){

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery("Select " + COLUMN_PRODUCT_QUALITY +" from "+ TABLE_PRODUCT +" Where " + COLUMN_PRODUCT_NAME + "= ?" + " And " + F_PRODUCT_COLUMN_ID_USER + "=" +id_user, new String[] {product_name});
        }
        return cursor;
    }

    public Cursor readData_ImportProduct(Integer id_user){
        String query = "Select "+ COLUMN_ID_IMPORT_PRODUCT + "," +
                COLUMN_PRODUCT_NAME +","+
                COLUMN_IMPORT_PRODUCT_CREATE_DAY +","+
                COLUMN_IMPORT_PRODUCT_OLD_QUALITY + "," +
                COLUMN_IMPORT_PRODUCT_NEW_QUALITY + "," +
                COLUMN_PRODUCT_IMAGE +
                " From "+ TABLE_IMPORT_PRODUCT + ","+ TABLE_PRODUCT +
                " Where "+ TABLE_IMPORT_PRODUCT +"."+ F_IMPORT_PRODUCT_COLUMN_ID_PRODUCT +"="+ TABLE_PRODUCT +"."+ COLUMN_ID_PRODUCT +
                " And " + F_IMPORT_PRODUCT_COLUMN_ID_USER + "=" + id_user +
                " ORDER BY " + COLUMN_ID_IMPORT_PRODUCT + " ASC ";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if (db != null){
            cursor = db.rawQuery(query,null);
        }
        return cursor;
    }

    public Cursor readAllData_ImportProduct(Integer import_product_id){
        String query = "Select "+ COLUMN_ID_IMPORT_PRODUCT + "," +
                COLUMN_TYPE_PRODUCT_NAME +","+
                COLUMN_PRODUCT_NAME +","+
                COLUMN_PRODUCT_PRICE + ","+
                COLUMN_IMPORT_PRODUCT_TOTAL_PRICE + ","+
                COLUMN_IMPORT_PRODUCT_OLD_QUALITY +","+
                COLUMN_IMPORT_PRODUCT_NEW_QUALITY +","+
                COLUMN_IMPORT_PRODUCT_CREATE_DAY +","+
                COLUMN_IMPORT_PRODUCT_CREATE_TIME +","+
                COLUMN_PRODUCT_IMAGE +
                " From "+ TABLE_IMPORT_PRODUCT + ","+ TABLE_PRODUCT + "," + TABLE_TYPE_PRODUCT +
                " Where "+ TABLE_IMPORT_PRODUCT +"."+ F_IMPORT_PRODUCT_COLUMN_ID_PRODUCT +"="+ TABLE_PRODUCT +"."+ COLUMN_ID_PRODUCT +
                " And " + TABLE_IMPORT_PRODUCT +"."+ F_IMPORT_PRODUCT_COLUMN_ID_TYPE_PRODUCT +"="+ TABLE_TYPE_PRODUCT +"."+ COLUMN_ID_TYPE_PRODUCT +
                " And " + COLUMN_ID_IMPORT_PRODUCT + " = " +import_product_id;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if (db != null){
            cursor = db.rawQuery(query,null);
        }
        return cursor;
    }

    public boolean deleteData_ImportProduct(Integer import_product_id){

        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_IMPORT_PRODUCT,COLUMN_ID_IMPORT_PRODUCT + "= ?", new String[] {String.valueOf(import_product_id)});
        if(result == -1){
            return false;
        }else{
            return true;
        }
    }
    //ImportProduct


    //Chart

    //SalesChart
    public Cursor readDataBarSales_Chart(Integer id_user, Integer year){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery("Select "+ COLUMN_BILL_CREATE_DAY + "," + COLUMN_BILL_TOTAL_PRICE + " from "+ TABLE_BILL +
                    " Where " + F_BILL_COLUMN_ID_USER + "=" + id_user + " And " + COLUMN_BILL_CREATE_DAY + " LIKE '%" + year +"%'",null);
        }
        return cursor;
    }
    //SalesChart


    //TotalQualityChart
    public Cursor readTypeProductQuality_Chart(Integer id_user){

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery("Select "+ COLUMN_TYPE_PRODUCT_NAME + " from "+ TABLE_TYPE_PRODUCT +
                    " Where " + F_TYPE_PRODUCT_COLUMN_ID_USER + "=" + id_user,null);
        }
        return cursor;
    }

    public Cursor getTypeProductID_Chart(String type_product_name, Integer id_user){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery("Select * from "+ TABLE_TYPE_PRODUCT +" Where "+ COLUMN_TYPE_PRODUCT_NAME +" = ?" +
                    " And " + F_TYPE_PRODUCT_COLUMN_ID_USER + "=" + id_user,new String[] {type_product_name});
        }
        return cursor;
    }

    public Cursor readDataBarQuality_Chart(Integer id_user, Integer year, String type_product){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery("Select "+ COLUMN_BILL_CREATE_DAY + "," + COLUMN_BILL_QUALITY + " from "+ TABLE_BILL +
                    " Where " + F_BILL_COLUMN_ID_USER + "=" + id_user + " And " + COLUMN_BILL_CREATE_DAY + " LIKE '%" + year +"%'" + " And " + F_BILL_COLUMN_ID_TYPE_PRODUCT + "=" + type_product ,null);
        }
        return cursor;
    }
    //TotalQualityChart

    //GrowthChart
    public Cursor readDataLineGrowth_Chart(Integer id_user, Integer year){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery("Select "+ COLUMN_BILL_CREATE_DAY + "," + COLUMN_BILL_TOTAL_PRICE + " from "+ TABLE_BILL +
                    " Where " + F_BILL_COLUMN_ID_USER + "=" + id_user + " And " + COLUMN_BILL_CREATE_DAY + " LIKE '%" + year +"%'",null);
        }
        return cursor;
    }
    //GrowthChart

    //Chart
}
