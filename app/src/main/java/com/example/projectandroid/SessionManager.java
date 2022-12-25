package com.example.projectandroid;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class SessionManager {

    private static  String TAG = SessionManager.class.getSimpleName();
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context context;

    int PRIVATE_MODE = 0;

    private static final String PREF_NAME = "AndroidCheckLogin";
    private static final String KEY_IS_LOGGED_IN = "IsLoggedIn";
    private static final String KEY_IS_SEC_TIME = "IsSecTime";
    private static final String KEY_IS_TURN_ON_BIO = "IsTurnOn";

    public SessionManager (Context context){
        this.context = context;
        pref = context.getSharedPreferences(PREF_NAME,PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setLogin (boolean isLoggedIn){
        editor.putBoolean(KEY_IS_LOGGED_IN,isLoggedIn);
        editor.commit();
        Log.d(TAG,"User login session modified");
    }

    public boolean isLoggedIn(){
        return pref.getBoolean(KEY_IS_LOGGED_IN,false);
    }

    public void setSecTime (boolean IsSecTime){
        editor.putBoolean(KEY_IS_SEC_TIME,IsSecTime);
        editor.commit();
    }

    public boolean isSecTime(){
        return pref.getBoolean(KEY_IS_SEC_TIME,false);
    }

    public void setId(String myID){
        editor.putString("saveID",myID);
        editor.commit();
    }

    public String getID (){
        String ID = pref.getString("saveID","");
        return ID;
    }

    public void setBio (boolean isTurnOnBio){
        editor.putBoolean(KEY_IS_TURN_ON_BIO,isTurnOnBio);
        editor.commit();
    }

    public boolean isTurnOnBio(){
        return pref.getBoolean(KEY_IS_TURN_ON_BIO,false);
    }


}
