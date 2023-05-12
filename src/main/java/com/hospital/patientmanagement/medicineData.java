package com.hospital.patientmanagement;


import java.util.Date;

public class medicineData {

    private Integer medicineId;
    private String brand;
    private String productName;
    private String type;
    private String status;
    private Double price;
    private Date medicine_date;

    public medicineData(Integer medicineId, String brand, String productName, String type, String status, Double price, Date medicine_date) {

        this.medicineId = medicineId;
        this.brand = brand;
        this.productName = productName;
        this.type = type;
        this.status = status;
        this.price = price;
        this.medicine_date = medicine_date;
    }

    public Integer getMedicineId() {
        return medicineId;
    }

    public String getBrand() {
        return brand;
    }

    public String getProductName() {
        return productName;
    }

    public String getType() {
        return type;
    }

    public String getStatus() {
        return status;
    }

    public Double getPrice() {
        return price;
    }

    public Date getMedicine_date() {
        return medicine_date;
    }


    public void setMedicineId(Integer medicineId) {
        this.medicineId = medicineId;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setMedicine_date(Date medicine_date) {
        this.medicine_date = medicine_date;
    }
}
