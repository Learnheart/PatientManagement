package com.hospital.patientmanagement;

import java.sql.Date;

public class customerData {

    private Integer customerId;
    private String type;
    private Integer medicineId;
    private String brand;
    private String productName;
    private Integer quantity;
    private double price;
    private Date date;

    public customerData(Integer customerId, String type, Integer medicineId, String brand, String productName, Integer quantity, double price, Date date) {
        this.customerId = customerId;
        this.type = type;
        this.medicineId = medicineId;
        this.brand = brand;
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
        this.date = date;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getMedicineId() {
        return medicineId;
    }

    public void setMedicineId(Integer medicineId) {
        this.medicineId = medicineId;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
