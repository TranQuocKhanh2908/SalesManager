package com.example.projectandroid.HelperClasses.HomeAdapter;

public class ListPromotionDashBoardHelperClass {

    String name_promotion, startDay_promotion, endDay_promotion;
    byte [] image_promotion;
    int idPromotion;

    public ListPromotionDashBoardHelperClass(String name_promotion, String startDay_promotion, String endDay_promotion, byte[] image_promotion, int idPromotion) {
        this.name_promotion = name_promotion;
        this.startDay_promotion = startDay_promotion;
        this.endDay_promotion = endDay_promotion;
        this.image_promotion = image_promotion;
        this.idPromotion = idPromotion;
    }

    public String getName_promotion() {
        return name_promotion;
    }

    public String getStartDay_promotion() {
        return startDay_promotion;
    }

    public String getEndDay_promotion() {
        return endDay_promotion;
    }

    public byte[] getImage_promotion() {
        return image_promotion;
    }

    public int getIdPromotion() {
        return idPromotion;
    }
}
