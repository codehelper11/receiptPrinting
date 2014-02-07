package com.dev.billing;

import com.dev.billing.service.BillingManager;
import com.dev.billing.swing.BillEntryView;
import com.dev.billing.swing.SwingConsole;

/**
 * User: droid
 * Date: 2/2/14
 * Time: 11:16 PM
 */
public class Bootstrap {

    public static void main(String[] args) {
        if(args.length!=1){
            System.out.println("Usage : java -jar billing.jar BILL_ENTRY|GENERATE_BILLS");
            return;
        }
        switch (args[0]){
            case "GENERATE_BILLS":
                new BillingManager().generateBills();
                break;
            case "BILL_ENTRY":
                SwingConsole.run(new BillEntryView(), 600, 600, "Bill Entry");
                break;
            default:
                System.err.println("INVALID SELECTION");
        }
    }
}
