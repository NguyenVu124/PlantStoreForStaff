package com.endterm.plantstorestaff.Model;

import java.util.ArrayList;
import java.util.HashMap;

public class Order {
    private String key, address, name, phone, status, total;
    private ArrayList<HashMap<String,PlantOrder>> plantOrders;

    public Order(){}

    public Order(String key, String address, String name, String phone, String status, String total, ArrayList<HashMap<String,PlantOrder>> plantOrders ) {
        this.key = key;
        this.address = address;
        this.name = name;
        this.phone = phone;
        this.status = status;
        this.total = total;
        this.plantOrders = plantOrders;

    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public ArrayList<HashMap<String,PlantOrder>> getPlantOrders() {
        return plantOrders;
    }

    public void setPlantOrders(ArrayList<HashMap<String,PlantOrder>> plantOrders) {
        this.plantOrders = plantOrders;
    }
}
