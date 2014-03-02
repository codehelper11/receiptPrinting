package com.dev.billing.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * User: droid
 * Date: 30/1/14
 * Time: 11:50 PM
 */
public class Bill {

    private String billNo;
    private Date billingDate;
    private List<Item> items;


    public Bill(String billNo, Date billingDate) {
        this.billNo = billNo;
        this.billingDate = billingDate;
        items = new ArrayList<>();
    }

    public interface Item {
        String getItemName();

        int getQuantity();

        double getRate();

        double getVatPercentage();

        double getTotal();

        double getVat();
    }


    private class InnerItem implements Item {
        String itemName;
        int quantity;
        double priceExcludingVat;
        double vatPercentage;
        double total;
        double singleItemVat;
        double rate;

        private InnerItem(String itemName, int quantity, double rate, double vatPercentage) {
            this.itemName = itemName;
            this.rate = rate;
            this.quantity = quantity;
            this.priceExcludingVat = rate/(1+vatPercentage*0.01);
            this.vatPercentage = vatPercentage;
            this.total = priceExcludingVat * quantity;
            this.singleItemVat = priceExcludingVat*vatPercentage/100;
        }


        @Override
        public String getItemName() {
            return itemName;
        }

        @Override
        public int getQuantity() {
            return quantity;
        }

        @Override
        public double getRate() {
            return rate;
        }

        @Override
        public double getVatPercentage() {
            return vatPercentage;
        }

        @Override
        public double getTotal() {
            return total;
        }

        @Override
        public double getVat() {
            return singleItemVat *quantity;
        }


        @Override
        public String toString() {
            return "InnerItem{" +
                    "itemName='" + itemName + '\'' +
                    ", quantity=" + quantity +
                    ", priceExcludingVat=" + priceExcludingVat +
                    ", vatPercentage=" + vatPercentage +
                    ", total=" + total +
                    '}' + "\n";
        }
    }

    public void addItem(String name, int quantity, double price, double vatPercentage) {
        items.add(new InnerItem(name, quantity, price, vatPercentage));
    }

    public List<Item> getItems() {
        return items;
    }

    public double getTotalVat() {
        double vat = 0;
        for (Item item : items) {
            vat += item.getVatPercentage() * item.getTotal() / 100;
        }
        return vat;
    }

    public double getTotalVatFivePercent() {
        double vat = 0;
        for (Item item : items) {
            if (Double.compare(item.getVatPercentage(), new Double(5.00)) == 0) {
                vat += item.getVatPercentage() * item.getTotal() / 100;
            }
        }
        return vat;
    }

    public double getTotalVatTwelvePercent() {
        double vat = 0;
        for (Item item : items) {
            if (Double.compare(item.getVatPercentage(), new Double(12.50)) == 0) {
                vat += item.getVatPercentage() * item.getTotal() / 100;
            }
        }
        return vat;
    }

    public double getTotalExcludingVat() {
        double total = 0;
        for (Item item : items) {
            total += item.getTotal();
        }
        return total;
    }

    public String getBillNo() {
        return billNo;
    }

    public Date getBillingDate() {
        return billingDate;
    }

    @Override
    public String toString() {
        return "Bill{" +
                "billNo='" + billNo + '\'' +
                ", billingDate=" + billingDate +
                ", TotalBillExcludingVat=" + getTotalExcludingVat() +
                ", TotalVat=" + getTotalVat() +
                ", Total=" + (getTotalVat() + getTotalExcludingVat()) +
                ", items=\n" + items +
                '}' + "\n";
    }
}
