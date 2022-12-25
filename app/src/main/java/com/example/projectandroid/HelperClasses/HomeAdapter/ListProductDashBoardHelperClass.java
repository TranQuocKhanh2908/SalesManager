package com.example.projectandroid.HelperClasses.HomeAdapter;

public class ListProductDashBoardHelperClass {

    private String name;
    private String quality;
    private byte[] image;
    int idProduct;

    public ListProductDashBoardHelperClass(String name, String quality, byte[] image, int idProduct) {
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
