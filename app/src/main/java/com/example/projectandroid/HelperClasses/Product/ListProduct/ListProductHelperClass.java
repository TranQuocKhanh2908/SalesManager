package com.example.projectandroid.HelperClasses.Product.ListProduct;

import java.io.Serializable;

public class ListProductHelperClass implements Serializable {

    private String name;
    private String quality;
    private byte[] image;
    int idProduct;

    public ListProductHelperClass(String name, String quality, byte[] image, int idProduct) {
        this.image = image;
        this.name = name;
        this.quality = quality;
        this.idProduct = idProduct;
    }

    public int getIdProduct() {
        return idProduct;
    }

    public String getName() {
        return name;
    }

    public String getQuality() {
        return quality;
    }

    public byte[] getImage() {
        return image;
    }
}
