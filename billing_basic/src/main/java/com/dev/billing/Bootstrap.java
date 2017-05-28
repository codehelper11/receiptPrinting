package com.dev.billing;

import com.dev.billing.service.ReceiptManager;

/**
 * User: droid
 * Date: 2/2/14
 * Time: 11:16 PM
 */
public class Bootstrap {

    public static void main(String[] args) {
        if(args.length!=1){
            System.out.println("Usage : java -jar billing.jar GENERATE_RECEIPTS");
            return;
        }
        switch (args[0]){
            case "GENERATE_RECEIPTS":
                new ReceiptManager().generateReceipts();
                break;
            default:
                System.err.println("INVALID SELECTION");
        }
    }
}
