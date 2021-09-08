package com.example.recycler_project;

public class ListProducts {
    int image;
    int idProduct;
    String descriptionName;
    double weightName, total;

    public ListProducts(int idProduct, int image, String descriptionName, double weightName, double total) {
        this.idProduct = idProduct;
        this.image = image;
        this.descriptionName = descriptionName;
        this.weightName = weightName;
        this.total = total;
    }

    public int getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getDescriptionName() {
        return descriptionName;
    }

    public void setDescriptionName(String descriptionName) { this.descriptionName = descriptionName; }

    public double getWeightName() {
        return weightName;
    }

    public void setWeightName(double weightName) {
        this.weightName = weightName;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
