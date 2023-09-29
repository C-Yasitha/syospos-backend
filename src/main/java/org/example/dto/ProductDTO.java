package org.example.dto;

import java.util.Date;

public class ProductDTO {

    private int id;
    private String productCode;
    private String productName;
    private String productDescription;
    private String productImage;
    private int lowLevel;
    private boolean isService;
    private double productWeight;
    private Date createdAt;
    private Date updatedAt;
    private String category;
    private String brand;
    private Float price;
    private Float qty;

    public ProductDTO(String productCode, String productName, String productDescription, String productImage,
                   int lowLevel, boolean isService, double productWeight, Date createdAt, Date updatedAt,
                   String category,String brand, Float price) {
        this.productCode = productCode;
        this.productName = productName;
        this.productDescription = productDescription;
        this.productImage = productImage;
        this.lowLevel = lowLevel;
        this.isService = isService;
        this.productWeight = productWeight;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.category = category;
        this.brand = brand;
        this.price = price;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public int getLowLevel() {
        return lowLevel;
    }

    public void setLowLevel(int lowLevel) {
        this.lowLevel = lowLevel;
    }

    public boolean isService() {
        return isService;
    }

    public void setService(boolean service) {
        isService = service;
    }

    public double getProductWeight() {
        return productWeight;
    }

    public void setProductWeight(double productWeight) {
        this.productWeight = productWeight;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Float getQty() {
        return qty;
    }

    public void setQty(Float qty) {
        this.qty = qty;
    }
}
