package com.dev.billing.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vinay on 29/5/17.
 */
public class ReceiptItemList {

    private List<ReceiptItem> receiptItemList = new ArrayList<>();

    public List<ReceiptItem> getReceiptItemList() {
        return receiptItemList;
    }

    public void addReceiptItem(ReceiptItem receiptItem) {
        this.receiptItemList.add(receiptItem);
    }
}
