package com.example.projectandroid.HelperClasses.Profile;

import android.graphics.Bitmap;

public class GetImageUserClass {

    private Bitmap imageUser;

    public GetImageUserClass(Bitmap imageUser) {
        this.imageUser = imageUser;
    }

    public Bitmap getImageUser() {
        return imageUser;
    }

    public void setImageUser(Bitmap imageUser) {
        this.imageUser = imageUser;
    }

}
