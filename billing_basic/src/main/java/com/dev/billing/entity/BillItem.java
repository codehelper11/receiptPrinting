package com.dev.billing.entity;

import java.util.Date;

/**
 * User: droid
 * Date: 30/1/14
 * Time: 10:50 PM
 */
public class BillItem {

    private String billNo;
    private int productId;
    private int productQuantity;
    private Date billingDate;

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }

    public Date getBillingDate() {
        return billingDate;
    }

    public void setBillingDate(Date billingDate) {
        this.billingDate = billingDate;
    }

    @Override
    public String toString() {
        return "BillItem{" +
                "billNo='" + billNo + '\'' +
                ", productId=" + productId +
                ", productQuantity=" + productQuantity +
                ", billingDate=" + billingDate +
                '}';
    }
}
