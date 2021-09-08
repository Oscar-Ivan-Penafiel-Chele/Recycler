package com.example.recycler_project;

public class ListRequestDetail {
    int image;
    int idProductDetail;
    String dateTimeMaterial, descriptionMaterialDetail;
    double weight, priceTotal;
    //double priceTotalProduct, priceTotal;

    public ListRequestDetail(int image, int idProductDetail, String dateTimeMaterial, String descriptionMaterialDetail, double weight, double priceTotal) {
        this.image = image;
        this.idProductDetail = idProductDetail;
        this.dateTimeMaterial = dateTimeMaterial;
        this.descriptionMaterialDetail = descriptionMaterialDetail;
        this.weight = weight;
        this.priceTotal = priceTotal;
//        this.priceTotalProduct = priceTotalProduct;
//        this.priceTotal = priceTotal;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public int getIdProductDetail() {
        return idProductDetail;
    }

    public void setIdProductDetail(int idProductDetail) {
        this.idProductDetail = idProductDetail;
    }

    public String getDateTimeMaterial() {
        return dateTimeMaterial;
    }

    public void setDateTimeMaterial(String dateTimeMaterial) {
        this.dateTimeMaterial = dateTimeMaterial;
    }

    public String getDescriptionMaterialDetail() {
        return descriptionMaterialDetail;
    }

    public void setDescriptionMaterialDetail(String descriptionMaterialDetail) {
        this.descriptionMaterialDetail = descriptionMaterialDetail;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getPriceTotal() {
        return priceTotal;
    }

    public void setPriceTotal(double priceTotal) {
        this.priceTotal = priceTotal;
    }

    //    public double getPriceTotalProduct() {
//        return priceTotalProduct;
//    }
//
//    public void setPriceTotalProduct(double priceTotalProduct) {
//        this.priceTotalProduct = priceTotalProduct;
//    }
//
//    public double getPriceTotal() {
//        return priceTotal;
//    }
//
//    public void setPriceTotal(double priceTotal) {
//        this.priceTotal = priceTotal;
//    }
}
