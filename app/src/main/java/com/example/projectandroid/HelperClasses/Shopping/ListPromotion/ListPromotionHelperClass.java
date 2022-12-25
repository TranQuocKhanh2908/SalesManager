package com.example.projectandroid.HelperClasses.Shopping.ListPromotion;

import java.io.Serializable;

public class ListPromotionHelperClass implements Serializable {

    byte[] imagePromotion;
    String namePromotion,presentPromotion,startDayPromotion,endDayPromotion;
    int idPromotion;

    public ListPromotionHelperClass(String namePromotion, String presentPromotion, String startDayPromotion, String endDayPromotion, byte[] imagePromotion, Integer idPromotion) {
        this.imagePromotion = imagePromotion;
        this.namePromotion = namePromotion;
        this.presentPromotion = presentPromotion;
        this.startDayPromotion = startDayPromotion;
        this.endDayPromotion = endDayPromotion;
        this.idPromotion = idPromotion;
    }

    public byte[] getImagePromotion() {
        return imagePromotion;
    }

    public String getNamePromotion() {
        return namePromotion;
    }

    public String getPresentPromotion() {
        return presentPromotion;
    }

    public String getStartDayPromotion() {
        return startDayPromotion;
    }

    public String getEndDayPromotion() {
        return endDayPromotion;
    }

    public Integer getIdPromotion() {
        return idPromotion;
    }
}
