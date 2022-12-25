package com.example.projectandroid.HelperClasses.Product.ListProduct;

import android.graphics.Bitmap;

public class GetImageProductClass {

    private Bitmap image;

    public GetImageProductClass(Bitmap image) {
        this.image = image;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }
}
