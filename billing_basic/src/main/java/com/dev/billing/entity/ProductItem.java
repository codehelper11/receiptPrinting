package com.dev.billing.entity;

/**
 * User: droid
 * Date: 29/1/14
 * Time: 11:33 PM
 */
public class ProductItem {

    private int id;
    private String productName;
    private String quantity;
    private double price;
    private double vatPercentage;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getVatPercentage() {
        return vatPercentage;
    }

    public void setVatPercentage(double vatPercentage) {
        this.vatPercentage = vatPercentage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProductItem that = (ProductItem) o;

        if (id != that.id) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return "ProductItem{" +
                "id=" + id +
                ", productName='" + productName + '\'' +
                ", quantity='" + quantity + '\'' +
                ", priceExcludingVat=" + price +
                ", vatPercentage=" + vatPercentage +
                '}';
    }
}
