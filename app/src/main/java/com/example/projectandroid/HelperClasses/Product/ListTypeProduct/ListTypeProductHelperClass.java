package com.example.projectandroid.HelperClasses.Product.ListTypeProduct;

public class ListTypeProductHelperClass {

    private String nameTypeProduct;
    private String descTypeProduct;
    Integer idTypeProduct;

    public ListTypeProductHelperClass(String nameTypeProduct, String descTypeProduct, int idTypeProduct) {
        this.nameTypeProduct = nameTypeProduct;
        this.descTypeProduct = descTypeProduct;
        this.idTypeProduct = idTypeProduct;
    }

    public String getNameTypeProduct() {
        return nameTypeProduct;
    }

    public String getDescTypeProduct() {
        return descTypeProduct;
    }

    public Integer getIdTypeProduct() {
        return idTypeProduct;
    }
}
