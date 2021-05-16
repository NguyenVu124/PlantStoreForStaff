package com.endterm.plantstorestaff.Model;

public class PlantModel{
    private String image;
    private String name;
    private String categoryId;
    private String price;

    public PlantModel(){}

    public PlantModel(String image, String name, String categoryId, String price) {
        this.image = image;
        this.name = name;
        this.categoryId = categoryId;
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}

