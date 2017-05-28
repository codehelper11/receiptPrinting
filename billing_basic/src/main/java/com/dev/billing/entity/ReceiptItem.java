package com.dev.billing.entity;

/**
 * Created by vinay on 29/5/17.
 */
public class ReceiptItem {

    private String serialNumber;
    private String date;
    private String name;
    private String address;
    private String phoneNumber;
    private String emailAddress;
    private String amountInWords;
    private String paymentType;
    private String chequeDate;
    private String bankName;
    private String bankBranch;
    private String pan;
    private String amount;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ReceiptItem{");
        sb.append("serialNumber='").append(serialNumber).append('\'');
        sb.append(", date='").append(date).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", address='").append(address).append('\'');
        sb.append(", phoneNumber='").append(phoneNumber).append('\'');
        sb.append(", emailAddress='").append(emailAddress).append('\'');
        sb.append(", amountInWords='").append(amountInWords).append('\'');
        sb.append(", paymentType='").append(paymentType).append('\'');
        sb.append(", chequeDate='").append(chequeDate).append('\'');
        sb.append(", bankName='").append(bankName).append('\'');
        sb.append(", bankBranch='").append(bankBranch).append('\'');
        sb.append(", pan='").append(pan).append('\'');
        sb.append(", amount='").append(amount).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getAmountInWords() {
        return amountInWords;
    }

    public void setAmountInWords(String amountInWords) {
        this.amountInWords = amountInWords;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getChequeDate() {
        return chequeDate;
    }

    public void setChequeDate(String chequeDate) {
        this.chequeDate = chequeDate;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankBranch() {
        return bankBranch;
    }

    public void setBankBranch(String bankBranch) {
        this.bankBranch = bankBranch;
    }

    public String getPan() {
        return pan;
    }

    public void setPan(String pan) {
        this.pan = pan;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
