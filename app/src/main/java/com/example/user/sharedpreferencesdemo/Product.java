package com.example.user.sharedpreferencesdemo;

import java.io.Serializable;

/**
 * Created by user on 11/27/16.
 */

public class Product implements Serializable{
    private int id;
    private String productName;
    private String productCategory;
    private String date;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }

    private double productPrice;
    private int availableProduct;
    private int productQuantity;

    public Product() {
    }

    public Product(String productName, String productCategory, double productPrice) {
        this.productName = productName;
        this.productCategory = productCategory;
        this.productPrice = productPrice;
    }

    public Product(int id, String productName, String productCategory, double productPrice) {
        this.id = id;
        this.productName = productName;
        this.productCategory = productCategory;
        this.productPrice = productPrice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAvailableProduct() {
        return availableProduct;
    }

    public void setAvailableProduct(int availableProduct) {
        this.availableProduct = availableProduct;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }
}
