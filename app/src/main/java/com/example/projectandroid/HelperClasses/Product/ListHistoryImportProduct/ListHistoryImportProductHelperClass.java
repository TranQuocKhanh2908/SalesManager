package com.example.projectandroid.HelperClasses.Product.ListHistoryImportProduct;

public class ListHistoryImportProductHelperClass {

    private byte[] imageImportProduct;
    String nameImportProduct,createDayImportProduct,oldQualityImportProduct, newQualityImportProduct;
    int idBImportProduct;

    public ListHistoryImportProductHelperClass(String nameImportProduct, String newQualityImportProduct, String createDayImportProduct, String oldQualityImportProduct, byte[] imageImportProduct,  int idBImportProduct) {
        this.imageImportProduct = imageImportProduct;
        this.nameImportProduct = nameImportProduct;
        this.createDayImportProduct = createDayImportProduct;
        this.oldQualityImportProduct = oldQualityImportProduct;
        this.newQualityImportProduct = newQualityImportProduct;
        this.idBImportProduct = idBImportProduct;
    }

    public byte[] getImageImportProduct() {
        return imageImportProduct;
    }

    public String getNameImportProduct() {
        return nameImportProduct;
    }

    public String getCreateDayImportProduct() {
        return createDayImportProduct;
    }

    public String getOldQualityImportProduct() {
        return oldQualityImportProduct;
    }

    public String getNewQualityImportProduct() {
        return newQualityImportProduct;
    }

    public int getIdBImportProduct() {
        return idBImportProduct;
    }
}
