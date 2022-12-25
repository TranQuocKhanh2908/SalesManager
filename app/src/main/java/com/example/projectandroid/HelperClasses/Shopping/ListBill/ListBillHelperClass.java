package com.example.projectandroid.HelperClasses.Shopping.ListBill;

import java.io.Serializable;

public class ListBillHelperClass implements Serializable {

    private byte[] imageBill;
    String nameBill,createDayBill,qualityBill;
    int idBill;


    public ListBillHelperClass(String nameBill, String qualityBill, String createDayBill, byte[] imageBill, int idBill) {
        this.imageBill = imageBill;
        this.nameBill = nameBill;
        this.qualityBill = qualityBill;
        this.createDayBill = createDayBill;
        this.idBill = idBill;

    }

    public byte[] getImageBill() {
        return imageBill;
    }

    public String getNameBill() {
        return nameBill;
    }

    public String getCreateDayBill() {
        return createDayBill;
    }

    public String getQualityBill() {
        return qualityBill;
    }

    public Integer getIdBill() {
        return idBill;
    }
}
