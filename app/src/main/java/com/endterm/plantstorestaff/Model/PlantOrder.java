package com.endterm.plantstorestaff.Model;

public class PlantOrder {
    private String price, productId,productName, quantity;

    public PlantOrder(){}

    public PlantOrder(String price, String productId, String productName, String quantity) {
        this.price = price;
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
