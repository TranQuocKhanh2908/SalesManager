package com.example.projectandroid.HelperClasses.HomeAdapter;

public class ListBillDashBoardHelperClass {

    String nameBill;
    String createDay, createTime;
    Integer idBill;
    private byte[] imageBill;

    public ListBillDashBoardHelperClass(String nameBill, String createDay, String createTime, Integer idBill, byte[] imageBill) {
        this.nameBill = nameBill;
        this.createDay = createDay;
        this.createTime = createTime;
        this.idBill = idBill;
        this.imageBill = imageBill;
    }

    public String getNameBill() {
        return nameBill;
    }

    public String getCreateDay() {
        return createDay;
    }

    public String getCreateTime() {
        return createTime;
    }

    public Integer getIdBill() {
        return idBill;
    }

    public byte[] getImageBill() {
        return imageBill;
    }
}
